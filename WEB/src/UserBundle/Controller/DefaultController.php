<?php

namespace UserBundle\Controller;

use FOS\UserBundle\Model\UserInterface;
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

        return $this->render('@User/afficherProfil.html.twig',array('user',$user));
    }

    /**
     * @Route("/delete/{id}",name="delete_profile")
     */
    public function deleteProfilAction($id)
    {
        $this->getDoctrine()->getManager()->remove($this->getUser());
        $this->getDoctrine()->getRepository("UserBundle:User")->supprimerUser($this->getUser()->getId());
        return $this->redirectToRoute("fos_user_security_login" );
    }
}
