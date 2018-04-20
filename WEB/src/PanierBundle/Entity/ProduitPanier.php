<?php

namespace PanierBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * ProduitPanier
 *
 * @ORM\Table(name="produit_panier")
 * @ORM\Entity
 */
class ProduitPanier
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_panier", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    public $idPanier;

    /**
     * @var integer
     *
     * @ORM\Column(name="id_produit", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="NONE")
     */
    public $idProduit;

    /**
     * @var string
     *
     * @ORM\Column(name="reference", type="string", length=50, nullable=false)
     */
    private $reference;

    /**
     * @var string
     *
     * @ORM\Column(name="libelle", type="string", length=50, nullable=false)
     */
    private $libelle;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255, nullable=false)
     */
    private $description;

    /**
     * @var float
     *
     * @ORM\Column(name="prix", type="float", precision=10, scale=0, nullable=true)
     */
    private $prix;

    /**
     * @var string
     *
     * @ORM\Column(name="taille", type="string", length=10, nullable=true)
     */
    private $taille;

    /**
     * @var string
     *
     * @ORM\Column(name="couleur", type="string", length=50, nullable=true)
     */
    private $couleur;

    /**
     * @var string
     *
     * @ORM\Column(name="texture", type="string", length=50, nullable=true)
     */
    private $texture;

    /**
     * @var float
     *
     * @ORM\Column(name="poids", type="float", precision=10, scale=0, nullable=true)
     */
    private $poids;

    /**
     * @var float
     *
     * @ORM\Column(name="quantite_vendu", type="float", precision=10, scale=0, nullable=false)
     */
    public $quantiteVendu;

    /**
     * @var float
     *
     * @ORM\Column(name="poids_vendu", type="float", precision=10, scale=0, nullable=true)
     */
    private $poidsVendu;

    /**
     * @var float
     *
     * @ORM\Column(name="prix_vente", type="float", precision=10, scale=0, nullable=false)
     */
    private $prixVente;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_ajout", type="datetime", nullable=false)
     */
    private $dateAjout;

    /**
     * @var boolean
     *
     * @ORM\Column(name="livree", type="boolean", nullable=false)
     */
    private $livree = '0';

    /**
     * ProduitPanier newProduitPanier.
     * @param int $idPanier
     * @param int $idProduit
     * @param string $reference
     * @param string $libelle
     * @param string $description
     * @param float $quantiteVendu
     * @param float $prixVente
     * @param bool $livree
     * @return ProduitPanier
     */
    public function newProduitPanier(int $idProduit, string $reference, string $libelle, string $description, float $quantiteVendu, float $prixVente, bool $livree)
    {

        $this->idProduit = $idProduit;
        $this->reference = $reference;
        $this->libelle = $libelle;
        $this->description = $description;
        $this->quantiteVendu = $quantiteVendu;
        $this->prixVente = $prixVente;
        $this->livree = $livree;
        return $this;
    }


    /**
     * Set idPanier
     *
     * @param integer $idPanier
     *
     * @return ProduitPanier
     */
    public function setIdPanier($idPanier)
    {
        $this->idPanier = $idPanier;

        return $this;
    }

    /**
     * Get idPanier
     *
     * @return integer
     */
    public function getIdPanier()
    {
        return $this->idPanier;
    }

    /**
     * Set idProduit
     *
     * @param integer $idProduit
     *
     * @return ProduitPanier
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
     * Set reference
     *
     * @param string $reference
     *
     * @return ProduitPanier
     */
    public function setReference($reference)
    {
        $this->reference = $reference;

        return $this;
    }

    /**
     * Get reference
     *
     * @return string
     */
    public function getReference()
    {
        return $this->reference;
    }

    /**
     * Set libelle
     *
     * @param string $libelle
     *
     * @return ProduitPanier
     */
    public function setLibelle($libelle)
    {
        $this->libelle = $libelle;

        return $this;
    }

    /**
     * Get libelle
     *
     * @return string
     */
    public function getLibelle()
    {
        return $this->libelle;
    }

    /**
     * Set description
     *
     * @param string $description
     *
     * @return ProduitPanier
     */
    public function setDescription($description)
    {
        $this->description = $description;

        return $this;
    }

    /**
     * Get description
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * Set prix
     *
     * @param float $prix
     *
     * @return ProduitPanier
     */
    public function setPrix($prix)
    {
        $this->prix = $prix;

        return $this;
    }

    /**
     * Get prix
     *
     * @return float
     */
    public function getPrix()
    {
        return $this->prix;
    }

    /**
     * Set taille
     *
     * @param string $taille
     *
     * @return ProduitPanier
     */
    public function setTaille($taille)
    {
        $this->taille = $taille;

        return $this;
    }

    /**
     * Get taille
     *
     * @return string
     */
    public function getTaille()
    {
        return $this->taille;
    }

    /**
     * Set couleur
     *
     * @param string $couleur
     *
     * @return ProduitPanier
     */
    public function setCouleur($couleur)
    {
        $this->couleur = $couleur;

        return $this;
    }

    /**
     * Get couleur
     *
     * @return string
     */
    public function getCouleur()
    {
        return $this->couleur;
    }

    /**
     * Set texture
     *
     * @param string $texture
     *
     * @return ProduitPanier
     */
    public function setTexture($texture)
    {
        $this->texture = $texture;

        return $this;
    }

    /**
     * Get texture
     *
     * @return string
     */
    public function getTexture()
    {
        return $this->texture;
    }

    /**
     * Set poids
     *
     * @param float $poids
     *
     * @return ProduitPanier
     */
    public function setPoids($poids)
    {
        $this->poids = $poids;

        return $this;
    }

    /**
     * Get poids
     *
     * @return float
     */
    public function getPoids()
    {
        return $this->poids;
    }

    /**
     * Set quantiteVendu
     *
     * @param float $quantiteVendu
     *
     * @return ProduitPanier
     */
    public function setQuantiteVendu($quantiteVendu)
    {
        $this->quantiteVendu = $quantiteVendu;

        return $this;
    }

    /**
     * Get quantiteVendu
     *
     * @return float
     */
    public function getQuantiteVendu()
    {
        return $this->quantiteVendu;
    }

    /**
     * Set poidsVendu
     *
     * @param float $poidsVendu
     *
     * @return ProduitPanier
     */
    public function setPoidsVendu($poidsVendu)
    {
        $this->poidsVendu = $poidsVendu;

        return $this;
    }

    /**
     * Get poidsVendu
     *
     * @return float
     */
    public function getPoidsVendu()
    {
        return $this->poidsVendu;
    }

    /**
     * Set prixVente
     *
     * @param float $prixVente
     *
     * @return ProduitPanier
     */
    public function setPrixVente($prixVente)
    {
        $this->prixVente = $prixVente;

        return $this;
    }

    /**
     * Get prixVente
     *
     * @return float
     */
    public function getPrixVente()
    {
        return $this->prixVente;
    }

    /**
     * Set dateAjout
     *
     * @param \DateTime $dateAjout
     *
     * @return ProduitPanier
     */
    public function setDateAjout($dateAjout)
    {
        $this->dateAjout = $dateAjout;

        return $this;
    }

    /**
     * Get dateAjout
     *
     * @return \DateTime
     */
    public function getDateAjout()
    {
        return $this->dateAjout;
    }

    /**
     * Set livree
     *
     * @param boolean $livree
     *
     * @return ProduitPanier
     */
    public function setLivree($livree)
    {
        $this->livree = $livree;

        return $this;
    }

    /**
     * Get livree
     *
     * @return boolean
     */
    public function getLivree()
    {
        return $this->livree;
    }
}
