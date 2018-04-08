<?php


namespace UserBundle\Redirection;

use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\RouterInterface;
use Symfony\Component\Security\Http\Logout\LogoutSuccessHandlerInterface;


class AfterLogoutRedirection implements LogoutSuccessHandlerInterface
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
     * @return RedirectResponse
     */
    public function onLogoutSuccess(Request $request)
    {
        // Get list of roles for current user
        $roles = $this->security->getToken()->getRoles();
        // Tranform this list in array
        $rolesTab = array_map(
            function ($role) {
                return $role->getRole();
            },
            $roles
        );
        // If is a commercial user or admin or super admin we redirect to the login area. Here we used FoseUserBundle bundle
        if (in_array('ROLE_CLIENT', $rolesTab, true) || in_array('ROLE_ARTISAN', $rolesTab, true) || in_array(
                'ROLE_ADMINISTRATEUR',
                $rolesTab,
                true
            )) {
            $response = new RedirectResponse($this->router->generate('fos_user_security_login'));
        } // otherwise we redirect user to the homepage of website
        else {
            $response = new RedirectResponse($this->router->generate('souk_homepage'));
        }

        return $response;
    }

}