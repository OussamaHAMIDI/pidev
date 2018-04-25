<?php

namespace SoukBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use EvaluationBundle\Entity\Evaluation;

class EvaluationController extends Controller
{
    
    /**
     * @Route("/api/evaluation/all")
     */
    public function allAction()
    {
        $evaluations = $this->getDoctrine()->getManager()
            ->getRepository('EvaluationBundle:Evaluation')
            ->findAll();
        foreach ($evaluations as $evaluation) {
            $evaluation->setDateCreation($evaluation->getDateCreation()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($evaluations);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/evaluation/find/{id}")
     */
    public function findAction($id)
    {
        $evaluation = $this->getDoctrine()->getManager()
            ->getRepository('EvaluationBundle:Evaluation')
            ->find($id);
        $evaluation->setDateCreation($evaluation->getDateCreation()->format('Y-m-d H:i:s'));
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($evaluation);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/evaluation/add")
     */
    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $evaluation= new Evaluation();
        $evaluation->setNote($request->get('note'));
        $evaluation->setDateCreation(new \DateTime($request->get('dateCreation')));
        if($request->get('idBoutique')){
            $boutique = $em->getRepository('BoutiqueBundle:Boutique')->find($request->get('idBoutique'));
            $evaluation->setIdBoutique($boutique);
        }
        if($request->get('idProduit')){
            $produit = $em->getRepository('ProduitBundle:Produit')->find($request->get('idProduit'));
            $evaluation->setIdProduit($produit);
        }
        $user = $em->getRepository('UserBundle:User')->find($request->get('idUser'));
        $evaluation->setIdUser($user);

        $em->persist($evaluation);
        $em->flush();

        $evaluation->setDateCreation($evaluation->getDateCreation()->format('Y-m-d H:i:s'));

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($evaluation);

        return new JsonResponse($formatted);
    }

   

}
