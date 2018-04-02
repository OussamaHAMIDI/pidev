<?php

namespace TombolaBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints\Date;
use Symfony\Component\Validator\Constraints\DateTime;
use Symfony\Component\validator\Constraints as Assert;
use UserBundle\Entity\User;

/**
 * Tombola
 *
 * @ORM\Table(name="tombola", indexes={@ORM\Index(name="id_vendeur", columns={"id_vendeur"}), @ORM\Index(name="id_gagnant", columns={"id_gagnant"})})
 * @ORM\Entity(repositoryClass="GestionTombolaBundle\Repository\TombolaRepository")
 * @ORM\HasLifecycleCallbacks
 */
class Tombola
{
    /**
     * @var integer
     *
     * @ORM\Column(name="id_tombola", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idTombola;

    /**
     * @var string
     *
     * @ORM\Column(name="titre", type="string", length=100, nullable=false)
     */
    private $titre;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=1000, nullable=false)
     */
    private $description;



    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_ajout", type="datetime", nullable=true)
     */
    private $dateAjout ;

    /**
     * @var DateTime
     * @Assert\GreaterThan("today")
     *
     * @ORM\Column(name="date_tirage", type="datetime", nullable=true)
     */
    private $dateTirage;

    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_gagnant", referencedColumnName="id", nullable=true)
     * })
     */
    private $idGagnant;

    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_vendeur", referencedColumnName="id", nullable=true)
     * })
     */
    private $idVendeur;

    /**
     * @ORM\Column(type="string",length=255, nullable=true)
     */
    public $path;

    public $file;

    /**
     * @var \DateTime
     *
     * @ORM\COlumn(name="updated_at",type="datetime", nullable=true)
     */
    private $updateAt;

    /**
     * @ORM\PostLoad()
     */
    public function postLoad()
    {
        $this->updateAt = new \DateTime();
    }

    /**
     * Tombola constructor.
     */
    public function __construct()
    {
        $this->dateAjout = new \DateTime();
    }


    /**
     * @return int
     */
    public function getIdTombola()
    {
        return $this->idTombola;
    }

    /**
     * @param int $idTombola
     */
    public function setIdTombola($idTombola)
    {
        $this->idTombola = $idTombola;
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
    public function setTitre($titre)
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
    public function setDescription($description)
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
    public function setDateAjout($dateAjout)
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
    public function setIdGagnant($idGagnant)
    {
        $this->idGagnant = $idGagnant;
    }

    /**
     * @return User
     */
    public function getIdVendeur()
    {
        return $this->idVendeur;
    }

    /**
     * @param User $idVendeur
     */
    public function setIdVendeur($idVendeur)
    {
        $this->idVendeur = $idVendeur;
    }

    public function getUploadRootDir()
    {
        return __dir__.'/../../../web/uploads';
    }

    public function getAbsolutePath()
    {
        return null === $this->path ? null : $this->getUploadRootDir().'/'.$this->path;
    }

    public function getAssetPath()
    {
        return 'uploads/'.$this->path;
    }

    /**
     * @ORM\Prepersist()
     * @ORM\Preupdate()
     */
    public function preUpload()
    {
        $this->tempFile = $this->getAbsolutePath();
        $this->oldFile = $this->getPath();
        $this->updateAt = new \DateTime();

        if (null !== $this->file)
            $this->path = sha1(uniqid(mt_rand(),true)).'.'.$this->file->guessExtension();
    }

    /**
     * @ORM\PostPersist()
     * @ORM\PostUpdate()
     */
    public function upload()
    {
        if (null !== $this->file) {
            $this->file->move($this->getUploadRootDir(),$this->path);
            unset($this->file);

            if ($this->oldFile != null) unlink($this->tempFile);
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
        if (file_exists($this->tempFile)) unlink($this->tempFile);
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
     * @return mixed
     */
    public function getFile()
    {
        return $this->file;
    }

    /**
     * @param mixed $file
     */
    public function setFile($file)
    {
        $this->file = $file;
    }


}
