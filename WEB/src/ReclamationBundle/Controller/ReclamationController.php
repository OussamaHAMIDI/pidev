<?php

namespace ReclamationBundle\Controller;

use ReclamationBundle\Entity\Reclamation;
use ReclamationBundle\Form\ReclamationType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class ReclamationController extends Controller
{


    public function afficherReclamationAction()
    {
        $em = $this->getDoctrine()->getManager();
        $reclamations = $em->getRepository("ReclamationBundle:Reclamation")->findAll();
        return $this->render('@Reclamation/Reclamation/afficher_reclamation.html.twig', array(
            "reclamations" => $reclamations
        ));
    }

    public function supprimerReclamationAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $reclamation = $em->getRepository(Reclamation::class)->find($id);
        $em->remove($reclamation);
        $em->flush();
        return $this->redirectToRoute('afficher_reclamation');
    }


    public function ajouterReclamationAction(Request $request, $idBoutique)
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $reclamation->setIdUser($this->container->get('security.token_storage')->getToken()->getUser());
        $form->handleRequest($request);
        if($form->isValid())
        {
            $em = $this->getDoctrine()->getManager();
            $reclamation->setIdBoutique($em->getRepository("BoutiqueBundle:Boutique")
                ->findOneBy(array('id' => $idBoutique)));
            $em=$this->getDoctrine()->getManager();
            $em->persist($reclamation);
            $em->flush();
        }
        //Merge
        return $this->render('@Reclamation/Reclamation/ajouter_reclamation.html.twig', array(
            "form" => $form->createView()
        ));


//        $reclamation = new Reclamation();
//        $form=$this->createForm(ReclamationType::class,$reclamation);
//        $form->handleRequest($request);
////        if($form->isValid())
////        {
//            $em=$this->getDoctrine()->getManager();
//            $reclamation = $form->getData();
//            $reclamation->setIdUser($this->container->get('security.token_storage')->getToken()->getUser());
//            $reclamation->setIdBoutique($em->getRepository("BoutiqueBundle:Boutique")
//            ->findOneBy(array('id' => $idBoutique)));
//            $em->persist($reclamation);
//            $em->flush();
////        }
//        return $this->render('@Reclamation/Reclamation/ajouter_reclamation.html.twig', array(
//            "form"=>$form->createView()
//        ));
    }

    public function searchAJAXReclamationAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $parameter = $request->get('key');
        $reclamations = $em->getRepository('ReclamationBundle:Reclamation')->rechercheAction($parameter);
        return $this->render('@Reclamation/Reclamation/rechercheAll.html.twig', array(
            "reclamations" => $reclamations
        ));
    }

}
