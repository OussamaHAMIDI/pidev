<?php

namespace ReclamationBundle\Controller;
use ReclamationBundle\Entity\Reclamation;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class ReclamationController extends Controller
{
    public function afficherReclamationAction(Request $request){
        $reclamation=new Reclamation();
        $reclamation->setIdUser($this->getUser());
        return $this->render("@Reclamation/Reclamation/reclamation.html.twig");
    }

    public function indexAction()
    {
        return $this->render('@Reclamation/Default/index.html.twig');
    }

}
