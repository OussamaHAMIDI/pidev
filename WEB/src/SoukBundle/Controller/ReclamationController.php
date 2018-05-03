<?php

namespace SoukBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use ReclamationBundle\Entity\Reclamation;

class ReclamationController extends Controller
{
    
    /**
     * @Route("/api/reclamation/all")
     */
    public function allAction()
    {
        $reclamations = $this->getDoctrine()->getManager()
            ->getRepository('ReclamationBundle:Reclamation')
            ->findAll();
        foreach ($reclamations as $reclamation) {
            $reclamation->setDateCreation($reclamation->getDateCreation()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reclamations);
        return new JsonResponse($formatted);
    }



    /**
     * @Route("/api/reclamation/allUser/{id}")
     */
    public function allUserAction($id)
    {
        $reclamation = $this->getDoctrine()->getManager()
            ->getRepository('ReclamationBundle:Reclamation')->findBy(array(
                'idUser'=>$id
            ));
        foreach ($reclamation as $rec) {
            $rec->setDateCreation($rec->getDateCreation()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reclamation);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/reclamation/delete/{id}")
     */
    public function deleteAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $reclamation = $this->getDoctrine()->getManager()
            ->getRepository('ReclamationBundle:Reclamation')->find($id);
        $reclamation->setDateCreation($reclamation->getDateCreation()->format('Y-m-d H:i:s'));
        $em->remove($reclamation);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reclamation);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/reclamation/find/{id}")
     */
    public function findAction($id)
    {
        $reclamation = $this->getDoctrine()->getManager()
            ->getRepository('ReclamationBundle:Reclamation')
            ->find($id);
        $reclamation->setDateCreation($reclamation->getDateCreation()->format('Y-m-d H:i:s'));
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reclamation);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/reclamation/add")
     */
    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $reclamation= new Reclamation();
        $reclamation->setDescription($request->get('description'));
        $reclamation->setDateCreation(new \DateTime());
        if($request->get('idBoutique')){
            $boutique = $em->getRepository('BoutiqueBundle:Boutique')->find($request->get('idBoutique'));
            $reclamation->setIdBoutique($boutique);
        }
        if($request->get('idProduit')){
            $produit = $em->getRepository('ProduitBundle:Produit')->find($request->get('idProduit'));
            $reclamation->setIdProduit($produit);
        }
        $user = $em->getRepository('UserBundle:User')->find($request->get('idUser'));
        $reclamation->setIdUser($user);

        $em->persist($reclamation);
        $em->flush();

        $reclamation->setDateCreation($reclamation->getDateCreation()->format('Y-m-d H:i:s'));

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reclamation);

        return new JsonResponse($formatted);
    }

}
