<?php

namespace BoutiqueBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use UserBundle\Entity\User;



/**
 * Boutique
 *
 * @ORM\Table(name="boutique", indexes={@ORM\Index(name="id_user", columns={"id_user"})})
 * @ORM\Entity(repositoryClass="BoutiqueBundle\Repository\BoutiqueRepository")
 * @ORM\HasLifecycleCallbacks
 */
class Boutique
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
     * @ORM\Column(name="nom", type="string", length=50, nullable=false)
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="adresse", type="string", length=100, nullable=true)
     */
    private $adresse;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_creation", type="datetime", nullable=false)
     */
    private $dateCreation;

    /**
     * @var float
     *
     * @ORM\Column(name="longitude", type="float", precision=10, scale=0, nullable=false)
     */
    private $longitude;

    /**
     * @var float
     *
     * @ORM\Column(name="altitude", type="float", precision=10, scale=0, nullable=false)
     */
    private $altitude;

    /**
     * @var string
     *
     * @ORM\Column(name="photo", type="blob", length=16777215, nullable=true)
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
    private $pathPhoto;

    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id")
     * })
     */
    private $idUser;



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
     * Set nom
     *
     * @param string $nom
     *
     * @return Boutique
     */
    public function setNom($nom)
    {
        $this->nom = $nom;

        return $this;
    }

    /**
     * Get nom
     *
     * @return string
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * Set adresse
     *
     * @param string $adresse
     *
     * @return Boutique
     */
    public function setAdresse($adresse)
    {
        $this->adresse = $adresse;

        return $this;
    }

    /**
     * Get adresse
     *
     * @return string
     */
    public function getAdresse()
    {
        return $this->adresse;
    }

    /**
     * @return \DateTime
     */
    public function getDateCreation()
    {
        return $this->dateCreation;
    }

    /**
     * @param \DateTime $dateCreation
     */
    public function setDateCreation($dateCreation)
    {
        $this->dateCreation = $dateCreation;
    }

    /**
     * Set longitude
     *
     * @param float $longitude
     *
     * @return Boutique
     */
    public function setLongitude($longitude)
    {
        $this->longitude = $longitude;

        return $this;
    }

    /**
     * Get longitude
     *
     * @return float
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * Set altitude
     *
     * @param float $altitude
     *
     * @return Boutique
     */
    public function setAltitude($altitude)
    {
        $this->altitude = $altitude;

        return $this;
    }

    /**
     * Get altitude
     *
     * @return float
     */
    public function getAltitude()
    {
        return $this->altitude;
    }

    /**
     * Set photo
     *
     * @param string $photo
     *
     * @return Boutique
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
     * Set idUser
     *
     * @param User $idUser
     *
     * @return Boutique
     */
    public function setIdUser(User $idUser = null)
    {
        $this->idUser = $idUser;

        return $this;
    }

    /**
     * Get idUser
     *
     * @return User
     */
    public function getIdUser()
    {
        return $this->idUser;
    }

    /**
     * @param mixed $pathPhoto
     */
    public function setPathPhoto($pathPhoto)
    {
        $this->pathPhoto = $pathPhoto;
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
    /**
     * @ORM\PostLoad()
     */
    public function postLoad()
    {
      //  $this->dateModif = new \DateTime();
    }

    public function getUploadRootDir()
    {
        return 'C:/xampp/htdocs/pidev/WEB/web/uploads';
//        return __dir__.'/../../../web/uploads';
//        return dirname(__DIR__, 4).'/uploads';
    }

    public function getAbsolutePath()
    {
        return null === $this->pathPhoto ? null : $this->getUploadRootDir().'/'.$this->getPathPhoto();
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
        $this->oldFile = $this->pathPhoto;
       // $this->dateModif = new \DateTime();


        if (null !== $this->file) {
            $this->pathPhoto = sha1(uniqid(mt_rand(), true)).'.'.$this->file->guessExtension();
        }
    }

    /**
     * @ORM\PostPersist()
     * @ORM\PostUpdate()
     */
    public function upload()
    {
        if (null !== $this->file) {
            $this->file->move($this->getUploadRootDir(), $this->pathPhoto);
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
