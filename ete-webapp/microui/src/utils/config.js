const MOCK_PFX = '/mock'
const APIV2 = '/api/v2'
const APIV3 = '/api/v3'

module.exports = {
  name: 'Firebrand Admin',
  prefix: 'eastcom',
  footerText: 'Firebrand Admin  © 2017 Eastcom',
  logo: '/logo.png',
  iconFontCSS: '/iconfont.css',
  iconFontJS: '/iconfont.js',
  CORS: [],
  openPages: ['/login'],
  apiPrefix: MOCK_PFX,
  MOCK_PFX,
  APIV2,
  api: {
    config:{
      def: `${MOCK_PFX}/sysconfigs/module/default`
    },
    user: {
      totalUser: `${APIV3}/index/totalUser`,
      badUser: `${APIV3}/index/badUser`,
      userCount: `${APIV3}/index/userCount`,
      portfolio: `${APIV3}/index/portfolio`,
      topCustomer: `${APIV3}/index/topCustomer`,
      topCell: `${APIV3}/index/topCell`,
      topApn: `${APIV3}/index/topApn`,
      topNe: `${APIV3}/index/topNe`
    },

  },
};
