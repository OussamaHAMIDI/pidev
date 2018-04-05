<?php

namespace TombolaBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use TombolaBundle\Form\TombolaType;
use TombolaBundle\Entity\Tombola;
use Symfony\Component\HttpFoundation\Request;

class TombolaController extends Controller
{
    /**
     * @Route("/ajouter", name="ajouter_tombola")
     */
    public function ajouterTombolaAction(Request $request){
        $tombola=new Tombola();
        $form=$this->createForm(TombolaType::class,$tombola);
        $tombola->setIdArtisan($this->getUser());
        //$form->handleRequest($request);
        if($request->isMethod('post')){
            $form->handleRequest($request);
            if($form->isValid())
            {
                $tombola = $form->getData();
                $em=$this->getDoctrine()->getManager();
                $em->persist($tombola);
                $em->flush();
                echo "amboula tzedit";
//                return $this->redirectToRoute("afficher_tombola_Profil");
            }
        }
        return $this->render("@Tombola/ajouterTombola.html.twig",array("form"=>$form->createView()));
    }
}
