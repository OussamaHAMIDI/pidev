<?php

namespace SoukBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use BoutiqueBundle\Entity\Boutique;

class BoutiqueController extends Controller
{

    /**
     * @Route("/api/boutique/all")
     */
    public function allAction()
    {
        $boutiques = $this->getDoctrine()->getManager()
            ->getRepository('BoutiqueBundle:Boutique')
            ->findAll();
        foreach ($boutiques as $boutique) {
            $boutique->setDateCreation($boutique->getDateCreation()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($boutiques);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/boutique/allUser/{id}")
     */
    public function allUserAction($id)
    {
        $boutiques = $this->getDoctrine()->getManager()
            ->getRepository('BoutiqueBundle:Boutique')->findBy(array(
                'idUser'=>$id
            ));
        foreach ($boutiques as $boutique) {
            $boutique->setDateCreation($boutique->getDateCreation()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($boutiques);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/boutique/find/{id}")
     */
    public function findAction($id)
    {
        $boutique = $this->getDoctrine()->getManager()
            ->getRepository('BoutiqueBundle:Boutique')
            ->find($id);
        $boutique->setDateCreation($boutique->getDateCreation()->format('Y-m-d H:i:s'));
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($boutique);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/boutique/delete/{id}")
     */
    public function deleteAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $boutique = $this->getDoctrine()->getManager()
            ->getRepository('BoutiqueBundle:Boutique')
            ->find($id);
        $em->remove($boutique);
        $em->flush();
        $boutique->setDateCreation($boutique->getDateCreation()->format('Y-m-d H:i:s'));
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($boutique);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/boutique/add")
     */
    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $boutique= new Boutique();
        $boutique->setNom($request->get('name'));
        $boutique->setDateCreation(new \DateTime($request->get('dateCreation')));
        $boutique->setLongitude($request->get('longitude'));
        $boutique->setAltitude($request->get('altitude'));
        $boutique->setAdresse($request->get('adresse'));
        $user = $em->getRepository('UserBundle:User')->find($request->get('idUser'));
        $boutique->setIdUser($user);

        $em->persist($boutique);
        $em->flush();

        $boutique->setDateCreation($boutique->getDateCreation()->format('Y-m-d H:i:s'));


        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($boutique);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/boutique/addImage/{id}")
     */
    public function ajouterPhotoAction(Request $request,$id)
    {
        $em = $this->getDoctrine()->getManager();

        $boutique = $em->getRepository('BoutiqueBundle:Boutique')->find($id);

        $dir = $boutique->getUploadRootDir().'/';

        $tempFile = $boutique->getAbsolutePath();

        if (file_exists($tempFile)) {
            unlink($tempFile);
        }

        foreach ($request->files as $uploadedFile) {
            $name = sha1(uniqid(mt_rand(), true)).'.'.$uploadedFile->guessExtension();
            $uploadedFile->move($dir, $name);
            $boutique->setPathPhoto($name);
        }

        $em->persist($boutique);
        $em->flush();

        $boutique->setDateCreation($boutique->getDateCreation()->format('Y-m-d H:i:s'));

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($boutique);

        return new JsonResponse($formatted);
    }
}
