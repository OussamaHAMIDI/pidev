<?php

namespace SoukBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use TombolaBundle\Entity\Tombola;

class TombolaController extends Controller
{
    /**
     * @Route("/api/tombola/all")
     */
    public function allAction()
    {
        $tombolas = $this->getDoctrine()->getManager()
            ->getRepository('TombolaBundle:Tombola')
            ->findAll();
        foreach ($tombolas as $tombola) {
            $tombola->setDateTirage($tombola->getDateTirage()->format('Y-m-d H:i:s'));
            $tombola->setDateAjout($tombola->getDateAjout()->format('Y-m-d H:i:s'));
            if ($tombola->getDateModif() != null) {
                $tombola->setDateModif($tombola->getDateModif()->format('Y-m-d H:i:s'));
            } else {
                $tombola->setDateModif("Jamais");
            }

        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tombolas);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/tombola/find/{id}")
     */
    public function findAction($id)
    {
        $tombola = $this->getDoctrine()->getManager()
            ->getRepository('TombolaBundle:Tombola')
            ->find($id);
        $tombola->setDateTirage($tombola->getDateTirage()->format('Y-m-d H:i:s'));
        $tombola->setDateAjout($tombola->getDateAjout()->format('Y-m-d H:i:s'));
        if ($tombola->getDateModif() != null) {
            $tombola->setDateModif($tombola->getDateModif()->format('Y-m-d H:i:s'));
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tombola);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/tombola/addOrEdit")
     */
    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $tombola = new Tombola();

        $id = $request->get('id');
        // MODIFICATION
        if ($id != null) {
            $tombola = $em->getRepository('TombolaBundle:Tombola')->find($id);
            $tombola->setDateModif(new \DateTime());
        }else{
            $artisan = $em->getRepository('UserBundle:User')->find($request->get('idArtisan'));
            $tombola->setIdArtisan($artisan);
            $tombola->setDateAjout(new \DateTime());
        }

        $a = $request->get('titre');
        if($a != null){
            $tombola->setTitre($a);
        }

        $a = $request->get('dateTirage');
        if($a != null){
            $tombola->setDateTirage(new \DateTime($a));
        }

        $a = $request->get('description');
        if($a != null){
            $tombola->setDescription($a);
        }

        $a = $request->get('idGagnant');
        if ($a != null) {
            $gagnant = $em->getRepository('UserBundle:User')->find($a);
            $tombola->setIdGagnant($gagnant);
        }

        $em->persist($tombola);
        $em->flush();
/*********************************************************************************************************************/

        $tombola->setDateAjout($tombola->getDateAjout()->format('Y-m-d H:i:s'));
        $a = $request->get('dateTirage');
        if($a != null){
            $tombola->setDateTirage($tombola->getDateTirage()->format('Y-m-d H:i:s'));
        }
        if ($id != null) {
            $tombola->setDateModif($tombola->getDateModif()->format('Y-m-d H:i:s'));
        }else{
            $tombola->setDateModif("Jamais");
        }

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tombola);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/tombola/addImage/{id}")
     */
    public function ajouterPhotoAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();

        $tombola = $em->getRepository('TombolaBundle:Tombola')->find($id);

        $tempFile = $tombola->getAbsolutePath();
    
        if (file_exists($tempFile)) {
            unlink($tempFile);
        }



        $dir = $tombola->getUploadRootDir().'/';

        foreach ($request->files as $uploadedFile) {
            $name = sha1(uniqid(mt_rand(), true)).'.'.$uploadedFile->guessExtension();
            $uploadedFile->move($dir, $name);
            $tombola->setPath($name);
        }

        $em->persist($tombola);
        $em->flush();



        $tombola->setDateTirage($tombola->getDateTirage()->format('Y-m-d H:i:s'));
        $tombola->setDateAjout($tombola->getDateAjout()->format('Y-m-d H:i:s'));

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tombola);

        return new JsonResponse($formatted);
    }

}
