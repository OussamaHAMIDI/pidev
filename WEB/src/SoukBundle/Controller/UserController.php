<?php

namespace SoukBundle\Controller;


use PanierBundle\Entity\Panier;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use UserBundle\Entity\User;


class UserController extends Controller
{
    /**
     * @Route("/api/user/all")
     */
    public function allAction()
    {
        $users = $this->getDoctrine()->getManager()
            ->getRepository('UserBundle:User')
            ->findAll();

            foreach ($users as $user) {
                if($user->getDateNaissance() != null)
                    $user->setDateNaissance($user->getDateNaissance()->format('Y-m-d'));
                if($user->getLastLogin() != null)
                    $user->setLastLogin($user->getLastLogin()->format('Y-m-d H:i:s'));

//                $user->setRoles('');
            }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($users);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/api/user/find/{id}")
     */
    public function findAction($id)
    {
        $user = $this->getDoctrine()->getManager()
            ->getRepository('UserBundle:User')
            ->find($id);
        if($user->getDateNaissance() != null)
            $user->setDateNaissance($user->getDateNaissance()->format('Y-m-d'));
        if($user->getLastLogin() != null)
            $user->setLastLogin($user->getLastLogin()->format('Y-m-d H:i:s'));
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($user);

        return new JsonResponse($formatted);
    }
    
    /**
     * @Route("/api/user/add")
     */
    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $user = new User();
        $user->setNom($request->get('nom'));
        $user->setPrenom($request->get('prenom'));
        $em->persist($user);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($user);
        return new JsonResponse($formatted);
    }



}
