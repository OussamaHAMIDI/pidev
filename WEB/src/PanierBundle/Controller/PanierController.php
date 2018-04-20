<?php

namespace PanierBundle\Controller;

use PanierBundle\Entity\Panier;
use PanierBundle\Entity\ProduitPanier;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;

/**
 * Panier controller.
 *
 * @Route("panier")
 */
class PanierController extends Controller
{


    /**
     * Creates a new panier entity.
     *
     * @Route("/new", name="panier_new")
     * @Method({"GET", "POST"})
     */
    public function newPanierAction(Request $request)
    {
        $panier = new Panier();
        $form = $this->createForm('PanierBundle\Form\PanierType', $panier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($panier);
            $em->flush();

            return $this->redirectToRoute('panier_default_index');
        }
    }

    /**
     * Creates a new ProduitPanier entity.
     *
     * @Route("/newproduitpanier", name="produitpanier_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $produitPanier = new ProduitPanier();
        $form = $this->createForm('PanierBundle\Form\ProduitPanierType', $produitPanier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($produitPanier);
            $em->flush();
            return $this->redirectToRoute('panier_default_index');
        }
    }

    /**
     * Displays a form to edit an existing panier entity.
     *
     * @Route("/{id}/editPanier", name="panier_edit")
     * @Method({"GET", "POST"})
     */
    public function editPanierAction(Request $request, Panier $panier)
    {
        $deleteForm = $this->createDeleteForm($panier);
        $editForm = $this->createForm('PanierBundle\Form\PanierType', $panier);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('panier_default_index');
        }
    }

    /**
     * Displays a form to edit an existing produitpanier entity.
     *
     * @Route("/{id}/edit", name="produitpanier_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, ProduitPanier $panier)
    {
        $deleteForm = $this->createDeleteForm($panier);
        $editForm = $this->createForm('PanierBundle\Form\PanierType', $panier);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('panier_default_index');
        }
    }


    /**
     * Deletes a panier entity.
     *
     * @Route("/{id}", name="panier_delete")
     * @Method("DELETE")
     */
    public function deletePanierAction(Request $request, Panier $panier)
    {
        $form = $this->createDeleteForm($panier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($panier);
            $em->flush();
        }
        return $this->redirectToRoute('panier_default_index');
    }

    /**
     * Deletes a produitpanier entity.
     *
     * @Route("/prodpanier/{id}", name="panier_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, ProduitPanier $panier)
    {
        $form = $this->createDeleteForm($panier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($panier);
            $em->flush();
        }
        return $this->redirectToRoute('panier_default_index');
    }

    /**
     * Creates a form to delete a panier entity.
     *
     * @param Panier $panier The panier entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Panier $panier)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('panier_delete', array('id' => $panier->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
