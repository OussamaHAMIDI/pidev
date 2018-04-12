<?php

namespace ProduitBundle\Controller;

use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Config\Definition\Builder\ParentNodeDefinitionInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

use ProduitBundle\Entity\Produit;
use ProduitBundle\Form\ProduitType;

class ProduitController extends Controller
{
    public function ajouterProduitAction(Request $request)
    {
        $produit = new Produit();
        $form=$this->createForm(ProduitType::class,$produit);
        $form->handleRequest($request);
        if($form->isValid())
        {
            $em=$this->getDoctrine()->getManager();
            $em->persist($produit);
            $em->flush();
            return $this->redirectToRoute('afficher_produits');
        }
        return $this->render('@Produit/Produit/ajouter_produit.html.twig', array(
            "form"=>$form->createView()
        ));
    }

    public function modifierProduitAction(Request $request,$id)
    {

        $em=$this->getDoctrine()->getManager();
        $produit=$em->getRepository("ProduitBundle:Produit")->find($id);
        if ($request->isMethod('POST'))
        {

            $produit->setLibelle($request->get('libelle'));
            $produit->setQuantite($request->get('quantite'));
            $produit->setAnimal($request->get('animal'));
            $produit->setPrix($request->get('prix'));
            $produit->setDescription($request->get('description'));
            $em->persist($produit);
            $em->flush();
            return $this->redirectToRoute('afficher_produits');
        }
        return $this->render('@Produit/Produit/modifier_produit.html.twig', array(
            'produit' => $produit));
    }


    public function supprimerProduitAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $produit= $em->getRepository(Produit::class)->find($id);
        $em->remove($produit);
        $em->flush();
        return $this->redirectToRoute('afficher_produits');
    }

    public function afficherProduitAction()
    {
        $em= $this->getDoctrine()->getManager();
        $produits=$em->getRepository("ProduitBundle:Produit")->affichage();
        return $this->render('ProduitBundle:Produit:afficher_produits.html.twig', array(
            "produits"=>$produits
        ));
    }


    public function afficherFrontProduitAction()
    {
        $em= $this->getDoctrine()->getManager();
        $produits=$em->getRepository("ProduitBundle:Produit")->affichage();
        return $this->render('@Produit/Produit/front_produits.html.twig', array(
            "produits"=>$produits
        ));
    }

    public function afficherProduitDetailAction($id)
    {
        $em= $this->getDoctrine()->getManager();
        $produit=$em->getRepository("ProduitBundle:Produit")->Details($id);
        return $this->render('@Produit/Produit/produits_details.html.twig', array(
            "produit"=>$produit
        ));
    }



    public function searchAjaxAction(Request $request)
    {
        if($request->isXmlHttpRequest())
        {
            $mot=$request->get('mot');
            $em=$this->getDoctrine();
            $centreD=$em->getRepository("ProduitBundle:Produit")->rechercheAjax($mot);
            //etape 1: initialiser le serializer
            $serializer=new Serializer(array(new ObjectNormalizer()));
            //etape 2 : transformation liste des objets
            $data=$serializer->normalize($centreD);
            //etape 3 : encodage format JSON
            return new JsonResponse($data);
        }
        return $this->render('ProduitBundle:Produit:afficher_produits.html.twig', array(

        ));
    }

}
