export const environment = {
  production: true,
  backendURL:  window.location.protocol + '//' + window.location.host,
  portalURL: window.location.protocol + '//' + window.location.host.replace('controle.', 'portal.').replace('30001','30002')
};
