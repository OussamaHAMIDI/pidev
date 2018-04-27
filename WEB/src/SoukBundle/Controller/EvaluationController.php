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
     * @Route("/api/evaluation/addBoutique")
     */
    public function ajouterBoutiqueAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $evaluation = $em->getRepository("EvaluationBundle:Evaluation")
            ->findOneBy(array('idBoutique' => $request->get('idBoutique'),
                'idUser' => $request->get('idUser')));
        if ($evaluation) {
            $evaluation->setNote($request->get('note'));
            $em->persist($evaluation);
            $em->flush();
        } else {
            $evaluation = new Evaluation();
            $boutique = $em->getRepository('BoutiqueBundle:Boutique')->find($request->get('idBoutique'));
            $evaluation->setIdBoutique($boutique);
            $user = $em->getRepository('UserBundle:User')->find($request->get('idUser'));
            $evaluation->setIdUser($user);

            $evaluation->setNote($request->get('note'));
            $em->persist($evaluation);
            $em->flush();
            $evaluation->setDateCreation($evaluation->getDateCreation()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($evaluation);
        return new JsonResponse($formatted);
    }

}
