<?php

namespace BoutiqueBundle\Controller;

use BoutiqueBundle\Form\BoutiqueType;
use BoutiqueBundle\Entity\Boutique;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;

class BoutiqueController extends Controller


{
    /**
     * @Route("/ajouter")
     */
    public function ajouterBoutiqueAction(Request $request)
    {

        $boutique = new Boutique();
        $form=$this->createForm(BoutiqueType::class,$boutique);
        $form->handleRequest($request);
        if($form->isValid())
        {
            $em=$this->getDoctrine()->getManager();
            $boutique->setIdUser($this->container->get('security.token_storage')->getToken()->getUser());
            $em->persist($boutique);
            $em->flush();
//            return $this->redirectToRoute('afficher_boutique');
        }
        return $this->render('@Boutique/Boutique/ajouter_Boutique.html.twig', array(
            "form"=>$form->createView()
        ));
    }

    public function modifierBoutiqueAction(Request $request,$id)
    {

        $em=$this->getDoctrine()->getManager();
        $boutique=$em->getRepository("BoutiqueBundle:Boutique")->find($id);
        if ($request->isMethod('POST'))
        {

            $boutique->setNom($request->get('nom'));
            $boutique->setAdresse($request->get('adresse'));

            $em->persist($boutique);
            $em->flush();
            return $this->redirectToRoute('afficher_boutique');
        }
        return $this->render('@Boutique/Boutique/modifier_Boutique.html.twig', array(
            'boutique' => $boutique));
    }


    public function supprimerBoutiqueAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $boutique= $em->getRepository(Boutique::class)->find($id);
        $em->remove($boutique);
        $em->flush();
        return $this->redirectToRoute('afficher_boutique');
    }

    public function afficherBoutiqueAction()
    {
        $em= $this->getDoctrine()->getManager();
        $boutiques=$em->getRepository("BoutiqueBundle:Boutique")->findAll();
        return $this->render('@Boutique/Boutique/afficher_boutique.html.twig', array(
            "boutiques"=>$boutiques
        ));
    }



    public function afficherBoutiqueDetailAction($id)
    {
        $em= $this->getDoctrine()->getManager();
        $boutique=$em->getRepository("BoutiqueBundle:Boutique")->Details($id);
        return $this->render('@Boutique/Boutique/boutiques_details.html.twig', array(
            "boutique"=>$boutique
        ));
    }





}
