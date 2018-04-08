<?php

namespace HistoriqueBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('HistoriqueBundle:Default:index.html.twig');
    }
}
