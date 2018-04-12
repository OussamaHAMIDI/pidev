<?php

namespace SoukBundle\Controller;

use ProduitBundle\Entity\Produit;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

<<<<<<< HEAD
use BoutiqueBundle\Entity\Boutique;
use SoukBundle\Entity\Produit2;

=======
>>>>>>> d44b1582fce9c8ff46d33941d635d653b181024d
class DefaultController extends Controller
{
    /**
     * @Route("/", name="souk_homepage")
     */
    public function indexAction()
    {
        $prod = new Produit('1','refart','testprod','testproood','14.5');
        $prod1 = new Produit('2','refart1','testprod55','testproood5585','4.5');
        $prod2 = new Produit('3','refart2','testprod7777','testproood858585','19.5');
        $this->get('session')->set('produits',[$prod,$prod1,$prod2]);
        $produits = $this->get('session')->get('produits');
        return $this->render('@Souk/Default/index.html.twig',array('produits'=>$produits));
    }

}
