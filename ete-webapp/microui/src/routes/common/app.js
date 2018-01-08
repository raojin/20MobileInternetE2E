/* global window */
import React from "react";
import NProgress from "nprogress";
import PropTypes from "prop-types";
import {connect} from "dva";
import {Layout} from "components";
import {classnames, config} from "utils";
import {Helmet} from "react-helmet";
import {withRouter} from "dva/router";
import "themes/index.less";
import "./app.less";
let lastHref

const App = ({ children, dispatch, app, loading, location }) => {
  const { styles } = Layout
  let { pathname } = location
  pathname = pathname.startsWith('/') ? pathname : `/${pathname}`
  const { iconFontJS, iconFontCSS, logo } = config
  const href = window.location.href

  if (lastHref !== href) {
    NProgress.start()
    if (!loading.global) {
      NProgress.done()
      lastHref = href
    }
  }

  return (
    <div>
      <Helmet>
        <title>ANTD ADMIN</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="icon" href={logo} type="image/x-icon" />
        {iconFontJS && <script src={iconFontJS} />}
        {iconFontCSS && <link rel="stylesheet" href={iconFontCSS} />}
      </Helmet>
      <div>
        <div className={styles.main}>
          <div className={styles.container}>
            <div className={styles.content}>
              {children}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

App.propTypes = {
  children: PropTypes.element.isRequired,
  location: PropTypes.object,
  dispatch: PropTypes.func,
  app: PropTypes.object,
  loading: PropTypes.object,
}

export default withRouter(connect(({ app, loading }) => ({ app, loading }))(App))
