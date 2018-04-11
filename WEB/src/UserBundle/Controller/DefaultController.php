<?php

namespace UserBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class DefaultController extends Controller
{
    /**
     * @Route("/afficher/{id}",name="show_profile")
     */
    public function afficherProfilAction($id)
    {
        $em=$this->getDoctrine()->getManager();
        $user=$em->getRepository("UserBundle:User")->findOneBy(array("id"=>$id));

        return $this->render('@UserBundle/afficherProfil.html.twig',array('profil',$user));
    }
}
