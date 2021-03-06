<?php


namespace UserBundle\Redirection;

use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\RouterInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationSuccessHandlerInterface;

class AfterLoginRedirection implements AuthenticationSuccessHandlerInterface
{
    /**
     * @var \Symfony\Component\Routing\RouterInterface
     */
    private $router;

    /**
     * @param RouterInterface $router
     */
    public function __construct(RouterInterface $router)
    {
        $this->router = $router;
    }

    /**
     * @param Request $request
     * @param TokenInterface $token
     * @return RedirectResponse
     */
    public function onAuthenticationSuccess(Request $request, TokenInterface $token)
    {
// Get list of roles for current user
        $roles = $token->getRoles();
// Tranform this list in array
        $rolesTab = array_map(function ($role) {
            return $role->getRole();
        }, $roles);
// If is a admin or admin we redirect to the backoffice area


        if (in_array('ROLE_ADMINISTRATEUR', $rolesTab, true))

            $redirection = new RedirectResponse($this->router->generate('souk_homepage'));

// otherwise we redirect user to the member area
        else  if (in_array('ROLE_ARTISAN', $rolesTab, true))

            $redirection = new RedirectResponse($this->router->generate('souk_homepage'));

// otherwise we redirect user to the member area
        else  if (in_array('ROLE_CLIENT', $rolesTab, true))

            $redirection = new RedirectResponse($this->router->generate('souk_homepage'));

        else

            $redirection = new RedirectResponse($this->router->generate('souk_homepage'));


        $time = new \DateTime();
        $result = $time->format('Y-m-d H:i:s');

        $txt = $token->getUsername().' login executed At : '.$result;
        $file = file_put_contents('logs.txt', $txt.PHP_EOL , FILE_APPEND | LOCK_EX);

        return $redirection;
    }
}