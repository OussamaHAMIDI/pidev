<?php

namespace EvaluationBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('EvaluationBundle:Default:index.html.twig');
    }
}
