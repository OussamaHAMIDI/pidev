<?php

namespace ProduitBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Produit
 *
 * @ORM\Table(name="produit")
 * @ORM\Entity(repositoryClass="ProduitBundle\Repository\ProduitRepository")
 */
class Produit
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

//    /**
//     * @ORM\ManyToOne(targetEntity="ProduitBundle\Entity\Boutique")
//     * @ORM\JoinColumn(name="boutique", referencedColumnName="id")
//     */
//    private $boutique;

    /**
     * @var string
     *
     * @ORM\Column(name="reference", type="string", length=255)
     */
    private $reference;

    /**
     * @var string
     *
     * @ORM\Column(name="libelle", type="string", length=255)
     */
    private $libelle;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255)
     */
    private $description;

    /**
     * @var int
     *
     * @ORM\Column(name="prix", type="integer")
     */
    private $prix;

    /**
     * @var string
     *
     * @ORM\Column(name="taille", type="string", length=255)
     */
    private $taille;

    /**
     * @var string
     *
     * @ORM\Column(name="couleur", type="string", length=255)
     */
    private $couleur;

    /**
     * @var string
     *
     * @ORM\Column(name="texture", type="string", length=255)
     */
    private $texture;

    /**
     * @var int
     *
     * @ORM\Column(name="poids", type="integer")
     */
    private $poids;


    /**
     * @var int
     *
     * @ORM\Column(name="date_creation", type="date")
     */
    private $date_creation;

    /**
     * @var string
     *
     * @ORM\Column(name="photo", type="string", length=255)
     * @Assert\NotBlank(message="Ajouter une image jpg")
     * @Assert\File(mimeTypes={ "image/jpeg" })
     *
     */
    private $photo;

    /**
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param int $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }

    public function setBoutique(Boutique $boutique = null)
    {
        $this->boutique = $boutique;

        return $this;
    }

    /**
     * Get categorie
     *
     * @return \ProduitBundle\Entity\Boutique
     */
    public function getBoutique()
    {
        return $this->boutique;
    }

    /**
     * @return string
     */
    public function getReference()
    {
        return $this->reference;
    }

    /**
     * @param string $reference
     */
    public function setReference($reference)
    {
        $this->reference = $reference;
    }

    /**
     * @return string
     */
    public function getLibelle()
    {
        return $this->libelle;
    }

    /**
     * @param string $libelle
     */
    public function setLibelle($libelle)
    {
        $this->libelle = $libelle;
    }

    /**
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * @param string $description
     */
    public function setDescription($description)
    {
        $this->description = $description;
    }

    /**
     * @return int
     */
    public function getPrix()
    {
        return $this->prix;
    }

    /**
     * @param int $prix
     */
    public function setPrix($prix)
    {
        $this->prix = $prix;
    }

    /**
     * @return string
     */
    public function getTaille()
    {
        return $this->taille;
    }

    /**
     * @param string $taille
     */
    public function setTaille($taille)
    {
        $this->taille = $taille;
    }

    /**
     * @return string
     */
    public function getCouleur()
    {
        return $this->couleur;
    }

    /**
     * @param string $couleur
     */
    public function setCouleur($couleur)
    {
        $this->couleur = $couleur;
    }

    /**
     * @return string
     */
    public function getTexture()
    {
        return $this->texture;
    }

    /**
     * @param string $texture
     */
    public function setTexture($texture)
    {
        $this->texture = $texture;
    }

    /**
     * @return int
     */
    public function getPoids()
    {
        return $this->poids;
    }

    /**
     * @param int $poids
     */
    public function setPoids($poids)
    {
        $this->poids = $poids;
    }

    /**
     * @return int
     */
    public function getDateCreation()
    {
        return $this->date_creation;
    }

    /**
     * @param int $date_creation
     */
    public function setDateCreation($date_creation)
    {
        $this->date_creation = $date_creation;
    }

    /**
     * @return string
     */
    public function getPhoto()
    {
        return $this->photo;
    }

    /**
     * @param string $photo
     */
    public function setPhoto($photo)
    {
        $this->photo = $photo;
    }




}
