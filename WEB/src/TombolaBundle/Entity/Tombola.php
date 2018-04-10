<?php

namespace TombolaBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

use Symfony\Component\Validator\Constraints\DateTime;
use Symfony\Component\Validator\Constraints as Assert;
use UserBundle\Entity\User;

/**
 * Tombola
 *
 * @ORM\Table(name="tombola", indexes={@ORM\Index(name="id_artisan", columns={"id_artisan"}), @ORM\Index(name="id_gagnant", columns={"id_gagnant"})})
 * @ORM\Entity(repositoryClass="TombolaBundle\Repository\TombolaRepository")
 * @ORM\HasLifecycleCallbacks
 */
class Tombola
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
     * *@Assert\Length(
     *      min = 5,
     *      max = 50,
     *      minMessage = "Titre doit contenir au moins  {{ limit }} characteres",
     *      maxMessage = "Titre doit contenir au maximum{{ limit }} characteres"
     * )
     * @ORM\Column(name="titre", type="string", length=100, nullable=false)
     */
    private $titre;

    /**
     * @var string
     * * @Assert\Length(
     *      min = 20,
     *      max = 500,
     *      minMessage = "Description doit contenir au moins  {{ limit }} characteres",
     *      maxMessage = "Description doit contenir au maximum{{ limit }} characteres"
     * )
     *
     * @ORM\Column(name="description", type="string", length=1000, nullable=false)
     */
    private $description;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_ajout", type="datetime", nullable=false)
     */
    private $dateAjout;

    /**
     * @var DateTime
     * @Assert\GreaterThan("today", message="Date tirage doit etre superieure à la date d'aujourd'hui")
     * @Assert\LessThan("1 year", message="Date tirage doit pas exceder une année compté d'aujourd'hui")
     *
     * @ORM\Column(name="date_tirage", type="datetime", nullable=true)
     */
    private $dateTirage;

    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_artisan", referencedColumnName="id", nullable=false, onDelete="CASCADE")
     * })
     */
    private $idArtisan;


    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_gagnant", referencedColumnName="id", nullable=true, onDelete="CASCADE")
     * })
     */
    private $idGagnant;


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
     * @var \DateTime
     *
     * @ORM\COlumn(name="date_modification",type="datetime", nullable=true)
     */
    private $dateModif;

    /**
     * @return int
     */
    public function getId(): int
    {
        return $this->id;
    }

    /**
     * @return string
     */
    public function getTitre()
    {
        return $this->titre;
    }

    /**
     * @param string $titre
     */
    public function setTitre(string $titre)
    {
        $this->titre = $titre;
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
    public function setDescription(string $description)
    {
        $this->description = $description;
    }

    /**
     * @return \DateTime
     */
    public function getDateAjout()
    {
        return $this->dateAjout;
    }

    /**
     * @param \DateTime $dateAjout
     */
    public function setDateAjout(\DateTime $dateAjout)
    {
        $this->dateAjout = $dateAjout;
    }

    /**
     * @return DateTime
     */
    public function getDateTirage()
    {
        return $this->dateTirage;
    }

    /**
     * @param DateTime $dateTirage
     */
    public function setDateTirage($dateTirage)
    {
        $this->dateTirage = $dateTirage;
    }

    /**
     * @return User
     */
    public function getIdGagnant()
    {
        return $this->idGagnant;
    }

    /**
     * @param User $idGagnant
     */
    public function setIdGagnant(User $idGagnant)
    {
        $this->idGagnant = $idGagnant;
    }

    /**
     * @return User
     */
    public function getIdArtisan()
    {
        return $this->idArtisan;
    }

    /**
     * @param User $idArtisan
     */
    public function setIdArtisan(User $idArtisan)
    {
        $this->idArtisan = $idArtisan;
    }

    /**
     * @return \DateTime
     */
    public function getDateModif()
    {
        return $this->dateModif;
    }

    /**
     * @param \DateTime $dateModif
     */
    public function setDateModif($dateModif)
    {
        $this->dateModif = $dateModif;
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
//        $this->dateModif = new \DateTime();


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
