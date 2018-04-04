<?php

namespace TombolaBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;

class TombolaType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('titre')
            ->add('description',TextareaType::class,['attr' => ['rows' => '5']])

            ->add('dateAjout',null,array(
                'attr'=>array('style'=>'display:none;'),
                'label_attr'=>array('style'=>'display:none;') ) )
            ->add('dateTirage',DateTimeType::class, array(
                'html5' => false,
                //'widget' => 'single_text',
                'input' => 'datetime', 'format' => 'yyyy-MM-dd  HH:mm:ss',
            ))
            ->add('file',FileType::class, array('required' => true, 'label' => 'Image du tombola',));


    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'TombolaBundle\Entity\Tombola'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'tombolabundle_tombola';
    }


}
