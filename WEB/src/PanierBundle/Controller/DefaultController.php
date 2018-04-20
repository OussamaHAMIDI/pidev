<?php

namespace PanierBundle\Controller;

use PanierBundle\Entity\Panier;
use PanierBundle\Entity\ProduitPanier;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;


class DefaultController extends Controller
{
    /**
     * @Route("/")
     */
    public function indexAction()
    {
        $produits = $this->get('session')->get('produits') ;
        $monpanier = $this->get('session')->get('monpanier') ;
        $user = $this->getUser();
        return $this->render('@Panier/Default/index.html.twig',array('produits'=>$produits,'monpanier'=>$monpanier,'user'=>$user));
    }

    /**
     * @Route("{id}/supprimer/")
     * @Method({"GET", "POST"})
     */
    public function supprimerProduitPanier(Request $request,$id)
    {

        if ($request->isXMLHttpRequest()) {
            $post_data = $request->request->all();

                    $data = json_decode($request->getContent());
            var_dump(json_decode($id));
            echo $id;
            echo json_decode($id);
             $produits = $this->get('session')->get('produits') ;
            foreach ($produits as $key => $object) {

                if ($object->idProduit == $id) {
                    $prod = $produits[$key];
                    $monpanier = $this->get('session')->get('monpanier');
                    $monpanier = $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())-(floatval($prod->getPrixVente())*floatval($prod->getQuantiteVendu())));
                    $this->get('session')->set('monpanier',$monpanier);
                    unset($produits[$key]);
                }
            }
            $reindex = array_values($produits);
        $produits = $reindex;
        $this->get('session')->set('produits',$produits);

            return new Response(json_encode($post_data));
        }

        return new Response('This is not ajax!', 400);


    }

    /**
     * @Route("{id}/ajouter/")
     * @Method({"GET", "POST"})
     */
    public function ajouterProduitPanier(Request $request,$id)
    {

        if ($request->isXMLHttpRequest()) {
            $post_data = $request->request->all();

            $produits = $this->get('session')->get('produits') ;
            $produit = $this->getDoctrine()->getManager()->getRepository('ProduitBundle:Produit')->findOneBy(array('id'=>$id));
            $prodpanier = new ProduitPanier();
            $prodpanier->newProduitPanier($produit->getId(),$produit->getReference(),$produit->getLibelle(),$produit->getDescription(),1,$produit->getPrix(),false);
var_dump($prodpanier);
            foreach ($produits as $key => $object) {

                if ($object->idProduit == $id) {
                    return new Response(json_encode("Déjà existant"));

                }


            }
            $monpanier = $this->get('session')->get('monpanier');
            $monpanier = $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())+(floatval($prodpanier->getPrixVente())*floatval($prodpanier->getQuantiteVendu())));
            $this->get('session')->set('monpanier',$monpanier);
            array_push($produits,$prodpanier);
            $reindex = array_values($produits);
            $produits = $reindex;
            $this->get('session')->set('produits',$produits);

            return new Response(json_encode("success"));
        }

        return new Response('This is not ajax!', 400);


    }

    /**
     * @Route("{id}/quantite/")
     * @Method({"GET", "POST"})
     */
    public function quantiteProduitPanier(Request $request,$id)
    {

        if ($request->isXMLHttpRequest()) {
            $post_data = $request->request->all();

            $data = json_decode($request->getContent());
            var_dump($post_data['qte']);
            echo $id;
            echo json_decode($id);
            $produits = $this->get('session')->get('produits') ;
            foreach ($produits as $key => $object) {

                if ($object->idProduit == $id) {
                    $prod = $produits[$key];
                    $monpanier = $this->get('session')->get('monpanier');
                    $monpanier = $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())-(floatval($prod->getPrixVente())*floatval($prod->getQuantiteVendu())));
                    $produits[$key]->quantiteVendu=$post_data['qte'];
                    $monpanier = $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())+(floatval($prod->getPrixVente())*floatval($prod->getQuantiteVendu())));
                    $this->get('session')->set('monpanier',$monpanier);
                }
            }
            $reindex = array_values($produits);
            $produits = $reindex;
            $this->get('session')->set('produits',$produits);

            return new Response(json_encode($post_data));
        }

        return new Response('This is not ajax!', 400);


    }

    /**
     * @Route("{/modelivraison")
     * @Method({"GET", "POST"})
     */
    public function modelivraison(Request $request)
    {

        if ($request->isXMLHttpRequest()) {
            $post_data = $request->request->all();
            var_dump($post_data['mode']);
            $monpanier = $this->get('session')->get('monpanier');
if($post_data['mode']=='ParPoste')
{
     $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())+floatval('12.0'));
     $monpanier->setFraisLivraison(12.0);
    $monpanier->setModeLivraison('ParPoste');
}elseif($post_data['mode']=='ADomicile')
{
    $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())+floatval('12.0'));
    $monpanier->setFraisLivraison(12.0);
    $monpanier->setModeLivraison('ADomicile');
}else
{
     $monpanier->setTotalTtc(floatval($monpanier->getTotalTtc())-floatval('12.0'));
    $monpanier->setFraisLivraison(0.0);
    $monpanier->setModeLivraison('SurPlace');
}
              $this->get('session')->set('monpanier',$monpanier);
            return new Response(json_encode($post_data));
        }
        return new Response('This is not ajax!', 400);
    }

    /**
     * @Route("{/modepayment")
     * @Method({"GET", "POST"})
     */
    public function modepayment(Request $request)
    {

        if ($request->isXMLHttpRequest()) {
            $post_data = $request->request->all();
            var_dump($post_data['mode']);
            $monpanier = $this->get('session')->get('monpanier');
            if($post_data['mode']=='Espece')
            {
                $monpanier->setModePaiement('Espece');
            }elseif($post_data['mode']=='Cheque')
            {
                $monpanier->setModePaiement('Cheque');
            }else
            {
                $monpanier->setModePaiement('Internet');
            }
            $this->get('session')->set('monpanier',$monpanier);
            return new Response(json_encode($post_data));
        }
        return new Response('This is not ajax!', 400);
    }

    /**
     * @Route("{/passercommande")
     * @Method({"GET", "POST"})
     */
    public function passercommande(Request $request)
    {



        if ($request->isXMLHttpRequest()) {


            $monpanier =$this->get('session')->get('monpanier');

            $produits = $this->get('session')->get('produits');

            $em = $this->getDoctrine()->getManager();
            $em->merge($monpanier);
            $em->flush();

            $nextId = $this->getDoctrine()->getManager()->getRepository('TombolaBundle:Tombola')->nextId();

            foreach ($produits as $proda)
        {
            $em = $this->getDoctrine()->getManager();
            $proda->setIdPanier($nextId["id"]);
            $proda->setDateAjout(new \DateTime());
            $em->persist($proda);
            $em->flush();
        }
        //GET PRODUITS
            $prods = array();
            foreach ($produits as $proda)
            {
                $em = $this->getDoctrine()->getManager()->getRepository('ProduitBundle:Produit')->findOneBy(array('id'=>$proda->idProduit));
               array_push($prods,$em);
            }
//GET PRODUITS
            // REGROUPER LES PRODUITS PAR BOUTIQUE
            $arr = array();
            foreach($prods as $pro)
            {
                $arr[$pro->getBoutique()->getId()][] = $pro;
            }


            ksort($arr, SORT_NUMERIC);

            // REGROUPER LES PRODUITS PAR BOUTIQUE
            //
            foreach($arr as $group)
            {
                $prodpanier = array();

                foreach ($group as $test)
                {
                    $user = $this->getDoctrine()
                        ->getManager()
                        ->getRepository('UserBundle:User')
                        ->findOneBy(array('id'=>$test->getBoutique()->getIdUser()));
                    foreach($produits as $obj) {

                        if ($test->getId() == $obj->getIdProduit()) {
                            array_push($prodpanier,$obj);
                            break;
                        }

                    }
                    $contenu_mail= \Swift_Message::newInstance()
                        ->setSubject('Commande')
                        ->setFrom(array('souklemdina@gmail.com'=>'Souk lemdina Team'))
                        ->setTo($user->getEmail())
                        ->setCharset('utf-8')
                        ->setContentType('text/html')
                        ->setBody($this->renderView('@Panier/Default/commande.html.twig',array('produits'=>$prodpanier,'client'=>$this->getUser()->getEmail())));
//                    $this->get('mailer')->send($contenu_mail);
                }
            }


            $panier = new Panier();
            $panier = $panier->newPanier(new \DateTime(),new \DateTime(),'0.0','0.0','temporelle','Espece','SurPlace',false,false,$this->getUser());

            if($monpanier->getModePaiement()=="Internet")
             {
            $contenu_mail= \Swift_Message::newInstance()
                ->setSubject('Facture')
                ->setFrom(array('souklemdina@gmail.com'=>'Souk lemdina Team'))
                ->setTo($this->getUser()->getEmail())
                ->setCharset('utf-8')
                ->setContentType('text/html')
                ->setBody($this->renderView('@Panier/Default/facture.html.twig',array('produits'=>$produits)));
//            $this->get('mailer')->send($contenu_mail);
                 //GENERATE PDF

                try {
                    $mpdf = new \Mpdf\Mpdf();
                    $mpdf->WriteHTML($this->renderView('@Panier/Default/pdf.html.twig',array('produits'=>$produits)));

                    $mpdf->Output('filename.pdf', \Mpdf\Output\Destination::FILE);
                } catch (\Mpdf\MpdfException $e) {

                    return new Response("EXCEPTIONPDF:::".$e->getMessage().$e);

                }
                 //GENERATE PDF
            $this->get('session')->set('produits',[]);
            $this->get('session')->set('monpanier',$panier);

            return new Response(json_encode("http://127.0.0.1:88/paypal/first.php?idpanier=".$nextId["id"]));

        }

            $contenu_mail= \Swift_Message::newInstance()
                ->setSubject('Facture')
                ->setFrom(array('souklemdina@gmail.com'=>'Souk lemdina Team'))
                ->setTo($this->getUser()->getEmail())
                ->setCharset('utf-8')
                ->setContentType('text/html')
                ->setBody($this->renderView('@Panier/Default/facture.html.twig',array('produits'=>$produits)));
//            $this->get('mailer')->send($contenu_mail);
            //GENERATE PDF

            try {
                $mpdf = new \Mpdf\Mpdf();
                $mpdf->WriteHTML($this->renderView('@Panier/Default/pdf.html.twig',array('produits'=>$produits)));

                $mpdf->Output('filename.pdf', \Mpdf\Output\Destination::FILE);
            } catch (\Mpdf\MpdfException $e) {

                return new Response("EXCEPTIONPDF:::".$e->getMessage().$e);

            }
            //GENERATE PDF
            $this->get('session')->set('produits',[]);
            $this->get('session')->set('monpanier',$panier);

            return new Response(json_encode("ok"));
        }
        return new Response('This is not ajax!', 400);
    }

    /**
     * @Route("/test")
     */
    public function panierAction()
    {

        $produits = $this->get('session')->get('produits') ;
        $monpanier = $this->get('session')->get('monpanier') ;

        return $this->render('@Panier/Default/panier.html.twig',array('produits'=>$produits,'monpanier'=>$monpanier));
    }
}
