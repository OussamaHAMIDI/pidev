<?php

namespace SoukBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    /**
     * @Route("/", name="souk_homepage")
     */
    public function indexAction()
    {
        return $this->render('@Souk/Default/index.html.twig');
    }
}
