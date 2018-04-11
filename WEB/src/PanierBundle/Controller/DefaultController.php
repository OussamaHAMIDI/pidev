<?php

namespace PanierBundle\Controller;

use PanierBundle\PanierBundle;

use PanierBundle\Entity\Panier;
use ProduitBundle\Entity\Produit;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class DefaultController extends Controller
{
    /**
     * @Route("/")
     */
    public function indexAction()
    {
        $produits = $this->get('session')->get('produits') ;
        $monpanier = $this->get('session')->get('monpanier') ;
        $user = $this->getUser();
        return $this->render('@Panier/Default/index.html.twig',array('produits'=>$produits,'monpanier'=>$monpanier,'user'=>$user));
    }


    /**
     * @Route("/test")
     */
    public function panierAction()
    {
        $monpanier = new Panier(1,new \DateTime(),new \DateTime(),'0.0','0.0','temporelle','Espece','SurPlace',false,false,$this->getUser());

        $prod = new Produit('1','refart','testprod','testproood','14.5');
        $prod1 = new Produit('2','refart1','testprod55','testproood5585','4.5');
        $prod2 = new Produit('3','refart2','testprod7777','testproood858585','19.5');

        $this->get('session')->set('produits',[$prod,$prod1,$prod2]);
        $this->get('session')->set('monpanier',$monpanier);

        $produits = $this->get('session')->get('produits') ;
        foreach ($produits as $proda)
        {
         $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())+floatval($proda->getPrix()));
        }
        return $this->render('@Panier/Default/panier.html.twig',array('produits'=>$produits,'monpanier'=>$monpanier));
    }
}
