<?php
/**
 * Created by PhpStorm.
 * User: monta
 * Date: 4/29/2018
 * Time: 11:16 PM
 */

namespace SoukBundle\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use PanierBundle\Entity\Panier;
use PanierBundle\Entity\ProduitPanier;
use UserBundle\Entity\User;

class PanierController extends Controller
{
    /**
     * @Route("/api/panier/all")
     */
    public function allAction()
    {
        $paniers = $this->getDoctrine()->getManager()
            ->getRepository('PanierBundle:Panier')
            ->findAll();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($paniers);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/panier/find/{id}")
     */
    public function findAction($id)
    {
        $panier = $this->getDoctrine()->getManager()
            ->getRepository('PanierBundle:Panier')
            ->find($id);


        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($panier);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/produitpanier/find/{id}")
     */
    public function findproduitAction($id)
    {
        $produits = $this->getDoctrine()->getManager()
            ->getRepository('PanierBundle:ProduitPanier')
            ->findBy(['IdPanier'=> $id]);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($produits);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/panier/add")
     */
    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $panier = new Panier();
//        $user = new User();
//      $user->setId($request->get('iduser'));
//        $panier->setIdUser($user);
        $panier->setTotalTTC($request->get('totalttc'));
        $panier->setDateCreation(new \DateTime());
        $panier->setFraisLivraison($request->get('fraislivraison'));
        $panier->setStatut($request->get('status'));
        $panier->setModePaiement($request->get('modepaiement'));
        $panier->setModeLivraison($request->get('modelivraison'));
        $panier->setEstLivre($request->get('estlivre'));
        $panier->setEstPaye($request->get('estpaye'));
        $user = $em->getRepository('UserBundle:User')->findOneBy(["id"=>$request->get('iduser')]);
        $panier->setIdUser($user);

        $em->persist($panier);
        $em->flush();



        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($panier);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/produitpanier/add")
     */
    public function ajouterproduitAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $nextId = $this->getDoctrine()->getManager()->getRepository('TombolaBundle:Tombola')->nextId();
        $produit = new ProduitPanier();
        var_dump($nextId);
        $produit->setIdPanier($nextId["id"]);


        $produit->setReference($request->get('reference'));
        $produit->setIdPanier($nextId["id"]);
        $produit->setIdProduit($request->get('idProduit'));
        $produit->setDescription($request->get('description'));
        $produit->setLivree($request->get('livree'));
        $produit->setPrixVente($request->get('prixvente'));
        $produit->setQuantiteVendu($request->get('quantitevendu'));
        $produit->setReference($request->get('reference'));

        $produit->setTexture($request->get('texture'));

        $produit->setCouleur($request->get('couleur'));
        $produit->setLibelle($request->get('libelle'));
        $produit->setPrix($request->get('prix'));

        $produit->setDateAjout(new \DateTime());


        $em->persist($produit);
        $em->flush();



        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($produit);

        return new JsonResponse($formatted);
    }

}