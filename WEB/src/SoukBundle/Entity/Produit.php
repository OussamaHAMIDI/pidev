<?php

namespace SoukBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Produit
 *
 * @ORM\Table(name="produit", indexes={@ORM\Index(name="id_boutique", columns={"id_boutique"})})
 * @ORM\Entity
 */
class Produit
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

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
     * @ORM\Column(name="description", type="string", length=200, nullable=false)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="prix", type="decimal", precision=7, scale=3, nullable=false)
     */
    private $prix;

    /**
     * @var string
     *
     * @ORM\Column(name="taille", type="string", length=50, nullable=true)
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
     * @var string
     *
     * @ORM\Column(name="poids", type="decimal", precision=7, scale=3, nullable=true)
     */
    private $poids;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_creation", type="datetime", nullable=false)
     */
    private $dateCreation;

    /**
     * @var string
     *
     * @ORM\Column(name="photo", type="blob", length=16777215, nullable=true)
     */
    private $photo;

    /**
     * @var string
     *
     * @ORM\Column(name="path_photo", type="string", length=180, nullable=true)
     */
    private $pathPhoto;



    /**
     * @var Boutique
     *
     * @ORM\ManyToOne(targetEntity="Boutique")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_boutique", referencedColumnName="id")
     * })
     */
    private $idBoutique;



    /**
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set reference
     *
     * @param string $reference
     *
     * @return Produit
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
     * @return Produit
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
     * @return Produit
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
     * @param string $prix
     *
     * @return Produit
     */
    public function setPrix($prix)
    {
        $this->prix = $prix;

        return $this;
    }

    /**
     * Get prix
     *
     * @return string
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
     * @return Produit
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
     * @return Produit
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
     * @return Produit
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
     * @param string $poids
     *
     * @return Produit
     */
    public function setPoids($poids)
    {
        $this->poids = $poids;

        return $this;
    }

    /**
     * Get poids
     *
     * @return string
     */
    public function getPoids()
    {
        return $this->poids;
    }

    /**
     * Set dateCreation
     *
     * @param \DateTime $dateCreation
     *
     * @return Produit
     */
    public function setDateCreation($dateCreation)
    {
        $this->dateCreation = $dateCreation;

        return $this;
    }

    /**
     * Get dateCreation
     *
     * @return \DateTime
     */
    public function getDateCreation()
    {
        return $this->dateCreation;
    }

    /**
     * Set photo
     *
     * @param string $photo
     *
     * @return Produit
     */
    public function setPhoto($photo)
    {
        $this->photo = $photo;

        return $this;
    }

    /**
     * Get photo
     *
     * @return string
     */
    public function getPhoto()
    {
        return $this->photo;
    }

    /**
     * Set idBoutique
     *
     * @param Boutique $idBoutique
     *
     */
    public function setIdBoutique(Boutique $idBoutique = null)
    {
        $this->idBoutique = $idBoutique;
    }

    /**
     * Get idBoutique
     *
     * @return \TestBundle\Entity\Boutique
     */
    public function getIdBoutique()
    {
        return $this->idBoutique;
    }

    /**
     * Set pathPhoto
     *
     * @param string $pathPhoto
     *
     * @return Produit
     */
    public function setPathPhoto($pathPhoto)
    {
        $this->pathPhoto = $pathPhoto;

        return $this;
    }

    /**
     * Get pathPhoto
     *
     * @return string
     */
    public function getPathPhoto()
    {
        return $this->pathPhoto;
    }
}
