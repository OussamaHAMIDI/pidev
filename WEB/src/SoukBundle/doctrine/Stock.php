<?php

namespace SoukBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Stock
 *
 * @ORM\Table(name="stock", indexes={@ORM\Index(name="fk_boutique_id2", columns={"id_boutique"}), @ORM\Index(name="id_produit", columns={"id_produit"})})
 * @ORM\Entity
 */
class Stock
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_produit", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idProduit;

    /**
     * @var integer
     *
     * @ORM\Column(name="id_boutique", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    private $idBoutique;

    /**
     * @var string
     *
     * @ORM\Column(name="quantitee", type="decimal", precision=7, scale=3, nullable=false)
     */
    private $quantitee;



    /**
     * Set idProduit
     *
     * @param integer $idProduit
     *
     * @return Stock
     */
    public function setIdProduit($idProduit)
    {
        $this->idProduit = $idProduit;

        return $this;
    }

    /**
     * Get idProduit
     *
     * @return integer
     */
    public function getIdProduit()
    {
        return $this->idProduit;
    }

    /**
     * Set idBoutique
     *
     * @param integer $idBoutique
     *
     * @return Stock
     */
    public function setIdBoutique($idBoutique)
    {
        $this->idBoutique = $idBoutique;

        return $this;
    }

    /**
     * Get idBoutique
     *
     * @return integer
     */
    public function getIdBoutique()
    {
        return $this->idBoutique;
    }

    /**
     * Set quantitee
     *
     * @param string $quantitee
     *
     * @return Stock
     */
    public function setQuantitee($quantitee)
    {
        $this->quantitee = $quantitee;

        return $this;
    }

    /**
     * Get quantitee
     *
     * @return string
     */
    public function getQuantitee()
    {
        return $this->quantitee;
    }
}
