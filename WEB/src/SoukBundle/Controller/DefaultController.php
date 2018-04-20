<?php

namespace SoukBundle\Controller;


use PanierBundle\Entity\Panier;
use PanierBundle\Entity\ProduitPanier;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use BoutiqueBundle\Entity\Boutique;


class DefaultController extends Controller
{
    /**
     * @Route("/", name="souk_homepage")
     */
    public function indexAction()
    {
        if($this->getUser() != null){
            $produits = $this->get('session')->get('produits') ;
            $monpanier = $this->get('session')->get('monpanier') ;
            $monpanier = new Panier();
            $monpanier = $monpanier->newPanier(new \DateTime(),new \DateTime(),'0.0','0.0','temporelle','Espece','SurPlace',false,false,$this->getUser());
            if(floatval($monpanier->getTotalTtc())==0.0)
            {
                $monpanier = new Panier();
                $monpanier = $monpanier->newPanier(new \DateTime(),new \DateTime(),'0.0','0.0','temporelle','Espece','SurPlace',false,false,$this->getUser());
                $this->get('session')->set('monpanier',$monpanier);
            }
            if($produits == null){
                $produits = array();
            }

            foreach ($produits as $proda)
            {
                $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())+(floatval($proda->getPrixVente())*floatval($proda->getQuantiteVendu())));
            }
        }
        else{
            $produits = array();
        }


        return $this->render('@Souk/Default/index.html.twig',array('produits'=>$produits));
    }



}
