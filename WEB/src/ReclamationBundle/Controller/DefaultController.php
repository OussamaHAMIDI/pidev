<?php

namespace ReclamationBundle\Controller;

use SoukBundle\Entity\Reclamation;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{

    /**
     * @Route("/afficher", name="afficher_reclamation")
     */
    public function afficherReclamationAction(Request $request){
        $reclamation=new Reclamation();
        $reclamation->setIdAdministrateur($this->getUser());
        return $this->render("@Reclamation/Reclamation/reclamation.html.twig");
    }

    public function indexAction()
    {
        return $this->render('@Reclamation/Default/index.html.twig');
    }

}
