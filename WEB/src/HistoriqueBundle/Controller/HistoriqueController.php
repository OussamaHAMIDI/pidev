<?php

namespace HistoriqueBundle\Controller;
use SoukBundle\Entity\Panier;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\User;

class HistoriqueController extends Controller
{


    public function indexAction()
    {
        return $this->render('@Historique/Default/index.html.twig');
    }

    public function afficherHistoriquesAction()
    {
        $em=$this->getDoctrine()->getManager();
        $user=$this->container->get('security.token_storage')->getToken()->getUser();
        $historiques=$em->getRepository("SoukBundle:Panier")->findBy( array('idUser' => $user, 'statut'=> 'Valide'));
        return $this->render('@Historique/Historique/afficher_historique.html.twig', array(
            'historiques' => $historiques));
    }

    public function afficherBoutiqueDetailAction($id)
    {
        $em= $this->getDoctrine()->getManager();
        $historique=$em->getRepository("SoukBundle:ProduitPanier")->findBy(array('idPanier' => $id));
        return $this->render('@Historique/Historique/afficher_historique_details.html.twig', array(
            "historique"=>$historique
        ));
    }

}
