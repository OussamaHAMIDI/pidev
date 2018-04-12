<?php

namespace ProduitBundle\Entity;

use BoutiqueBundle\Entity\Boutique;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
<<<<<<< HEAD

=======
>>>>>>> d44b1582fce9c8ff46d33941d635d653b181024d

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
<<<<<<< HEAD

    /**
     * @ORM\ManyToOne(targetEntity="BoutiqueBundle\Entity\Boutique")
     * @ORM\JoinColumn(name="boutique", referencedColumnName="id")
     */
    private $boutique;

=======
    /**
     * @ORM\ManyToOne(targetEntity="BoutiqueBundle\Entity\Boutique")
    * @ORM\JoinColumn(name="boutique", referencedColumnName="id")
     */
       private $boutique;
>>>>>>> d44b1582fce9c8ff46d33941d635d653b181024d

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
     * @ORM\Column(type="string",length=255, nullable=true)
     *
     *
     *
     * *@Assert\File(
     *     maxSize="3M",
     *     mimeTypes={"image/png", "image/jpeg", "image/jpeg"},maxSizeMessage="La taille du fichier est trop grande ({{ size }} {{ suffix }}).
     *    La taille maximale autorisée est {{ limit }} {{ suffix }}"
     * )
     */
    private $path;

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

    /**
     * @param Boutique $boutique
     * @return Boutique
     */

    public function setBoutique(Boutique $boutique = null)
    {
        $this->boutique = $boutique;

        return $this;
    }

    /**
     * Get categorie
     *
     * @return \BoutiqueBundle\Entity\Boutique
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
    /**
     * @return mixed
     */
    public function getPath()
    {
        return $this->path;
    }

    /**
     * @param mixed $path
     */
    public function setPath($path)
    {
        $this->path = $path;
    }


    /**
     * Tombola constructor.
     */
    public function __construct()
    {
        $this->dateAjout = new \DateTime();
    }

    /**
     * @ORM\PostLoad()
     */
    public function postLoad()
    {
        #$this->dateModif = new \DateTime();
    }

    public function getUploadRootDir()
    {
        return 'C:/xampp/htdocs/pidev/WEB/web/uploads';
//        return __dir__.'/../../../web/uploads';
//        return dirname(__DIR__, 4).'/uploads';
    }

    public function getAbsolutePath()
    {
        return null === $this->path ? null : $this->getUploadRootDir().'/'.$this->getPath();
    }


    public $file;


    /**
     * @ORM\PrePersist()
     * @ORM\PreUpdate()
     */
    public function preUpload()
    {
        $t = $this->getAbsolutePath();

        if (strlen($t) > strlen($this->getUploadRootDir()) + 41) {
            $this->tempFile = substr($t, strlen($this->getUploadRootDir())+1);
        } else {
            $this->tempFile = $this->getAbsolutePath();
        }
        $this->oldFile = $this->path;
        $this->dateModif = new \DateTime();


        if (null !== $this->file) {
            $this->path = sha1(uniqid(mt_rand(), true)).'.'.$this->file->guessExtension();
        }
    }

    /**
     * @ORM\PostPersist()
     * @ORM\PostUpdate()
     */
    public function upload()
    {
        if (null !== $this->file) {
            $this->file->move($this->getUploadRootDir(), $this->path);
            unset($this->file);

            if ($this->oldFile != null) {
                unlink($this->tempFile);
            }
        }
    }

    /**
     * @ORM\PreRemove()
     */
    public function preRemoveUpload()
    {
        $this->tempFile = $this->getAbsolutePath();
    }

    /**
     * @ORM\PostRemove()
     */
    public function removeUpload()
    {
        if (file_exists($this->tempFile)) {
            unlink($this->tempFile);
        }
    }



}
