<?php
/**
 * Created by PhpStorm.
 * User: monta
 * Date: 4/30/2018
 * Time: 10:46 AM
 */

namespace SoukBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use ProduitBundle\Entity\Produit;


class ProduitController
{
    /**
     * @Route("/api/produit/all")
     */
    public function allAction()
    {
        $produits = $this->getDoctrine()->getManager()
            ->getRepository('ProduitBundle:Produit')
            ->findAll();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($produits);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/produit/find/{id}")
     */
    public function findAction($id)
    {
        $produit = $this->getDoctrine()->getManager()
            ->getRepository('ProduitBundle:Produit')
            ->find($id);


        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($produit);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/produit/add")
     */
    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $produit = new Produit();
        $produit->setReference($request->get('reference'));
        $produit->setQuantite($request->get('quantite'));
        $produit->setTexture($request->get('texture'));
        $produit->setDescription($request->get('description'));
        $produit->setCouleur($request->get('couleur'));
        $produit->setLibelle($request->get('libelle'));
        $produit->setPrix($request->get('prix'));
        $produit->setDateCreation(new \DateTime());

        $boutique = $em->getRepository('BoutiqueBundle:Boutique')->find($request->get('idBoutique'));
        $produit->setBoutique($boutique);


        $em->persist($produit);
        $em->flush();



        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($produit);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/produit/addImage/{id}")
     */
    public function ajouterPhotoAction(Request $request,$id)
    {
        $em = $this->getDoctrine()->getManager();
        $produit = new Produit();
        $produit = $em->getRepository('ProduitBundle:Produit')->find($id);

        $dir = $produit->getUploadRootDir().'/';

        foreach ($request->files as $uploadedFile) {
            $name = sha1(uniqid(mt_rand(), true)).'.'.$uploadedFile->guessExtension();
            $uploadedFile->move($dir, $name);
            $produit->setPath($name);
        }

        $em->persist($produit);
        $em->flush();



        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($produit);

        return new JsonResponse($formatted);
    }

}