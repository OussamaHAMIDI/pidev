<?php

namespace BoutiqueBundle\Controller;

use BoutiqueBundle\Form\BoutiqueEditType;
use BoutiqueBundle\Form\BoutiqueType;
use BoutiqueBundle\Entity\Boutique;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;

class BoutiqueController extends Controller


{
    /**
     * @Route("/ajouter")
     */
    public function ajouterBoutiqueAction(Request $request)
    {

        $boutique = new Boutique();
        $form=$this->createForm(BoutiqueType::class,$boutique);
        $form->handleRequest($request);
        if($form->isValid())
        {
            /**
             * @var UploadedFile
             */
            $file=$boutique->getPathPhoto();
            $filename=md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('image_directory'),$filename);
            $boutique->setPathPhoto($filename);
            $em=$this->getDoctrine()->getManager();
            $boutique->setIdUser($this->container->get('security.token_storage')->getToken()->getUser());
            $em->persist($boutique);
            $em->flush();
//            return $this->redirectToRoute('afficher_boutique');
        }
        return $this->render('@Boutique/Boutique/ajouter_Boutique.html.twig', array(
            "form"=>$form->createView()
        ));
    }

    public function modifierBoutiqueAction(Request $request,$id)
    {

        $em=$this->getDoctrine()->getManager();
        $boutique=$em->getRepository("BoutiqueBundle:Boutique")->find($id);
        $form=$this->createForm(BoutiqueEditType::class,$boutique);
        $form->handleRequest($request);
        if($form->isValid()) {
            $em->persist($boutique);
            $em->flush();
            return $this->redirectToRoute('afficher_boutique');
        }
        return $this->render('@Boutique/Boutique/modifier_Boutique.html.twig', array(
            'form'=>$form->createView(),
            'boutique' => $boutique));
    }


    public function supprimerBoutiqueAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $boutique= $em->getRepository(Boutique::class)->find($id);
        $em->remove($boutique);
        $em->flush();
        return $this->redirectToRoute('afficher_boutique');
    }

    public function afficherBoutiqueAction()
    {
        $em= $this->getDoctrine()->getManager();
        $boutiques=$em->getRepository("BoutiqueBundle:Boutique")->findAll();

        return $this->render('@Boutique/Boutique/afficher_boutique.html.twig', array(
            "boutiques"=>$boutiques
        ));
    }
    public function afficherBoutiqueArtisanAction($id)
    {
        $em= $this->getDoctrine()->getManager();
        $boutiques=$em->getRepository("BoutiqueBundle:Boutique")->findBy(array('idUser' => $this->container->get('security.token_storage')->getToken()->getUser()));

        return $this->render('@Boutique/Boutique/afficher_boutique.html.twig', array(
            "boutiques"=>$boutiques
        ));
    }



    public function afficherBoutiqueDetailAction($id)
    {
        $em= $this->getDoctrine()->getManager();
        $boutique=$em->getRepository("BoutiqueBundle:Boutique")->find($id);
        return $this->render('@Boutique/Boutique/afficher_details.html.twig', array(
            "boutique"=>$boutique
        ));
    }
    public function chercherBoutiqueAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $searchParameter = $request->get('key');
        $boutiques = $em->getRepository('Boutique')->rechercheAction($searchParameter);

        return $this->render("@Boutique/Boutique/recherche.html.twig", array('boutiques' => $boutiques));

    }



}
