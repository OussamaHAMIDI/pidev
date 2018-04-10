<?php

namespace StatistiqueBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('StatistiqueBundle:Default:index.html.twig');
    }
}
