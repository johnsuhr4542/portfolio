import React, { Component } from 'react'
import queryString from 'query-string';
import axios from 'axios';

import domain from '../lib/url';
import { List, Paging, Search } from '../components';

class ListContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      searchOption: 'all',
      searchValue: '',
      articles: undefined,
      pagination: undefined
    }
    this.fetchData = this.fetchData.bind(this);
    this.pageTo = this.pageTo.bind(this);
    this.handleSearch = this.handleSearch.bind(this);
  }

  // initially fetch data.
  // if there is page information, fetch data according to given page.
  componentDidMount = async () => {
    const { page, searchOption, searchValue } = queryString.parse(this.props.location.search);
    if (searchOption) {
      await this.setState({
        searchOption: searchOption
      })
    }
    if (searchValue) {
      await this.setState({
        searchValue: searchValue
      })
    }
    if (page) {
      this.fetchData(page);
    } else {
      this.fetchData(1);
    }
  }

  // detect change of page and refresh list data.
  componentDidUpdate = async prevProps => {
    const query = queryString.parse(prevProps.location.search);

    const prevPage = query.page;
    const prevSearchOption = query.searchOption;
    const prevSearchValue = query.searchValue;

    const { page, searchOption, searchValue } = queryString.parse(this.props.location.search);

    // if detected some changes of page, searchOption, searchValue,
    // fetch data again.
    if (page !== prevPage || 
        searchOption !== prevSearchOption || 
        searchValue !== prevSearchValue) {
      this.fetchData(page);
    }
  }

  fetchData = page => {
    const { searchOption, searchValue } = this.state;
    axios
      .get(`${domain}/article?page=${page}&searchOption=${searchOption}&searchValue=${searchValue}`)
      .then(res => {
        if (res.status === 200) {
          const { articles, pagination } = res.data;
          this.setState({
            articles: articles,
            pagination: pagination
          })
        } else {
          // may return 204 no content.
          window.alert('데이터가 존재하지 않습니다.');
          return;
        }
      })
      .catch(err => {
        window.alert('통신 실패');
        console.log(err);
        return;
      });
  }

  // this is for page navigation. used in pagination object
  pageTo = page => {
    const { history } = this.props;
    const { searchOption, searchValue } = this.state;
    history.push(`/list?page=${page}&searchOption=${searchOption}&searchValue=${searchValue}`);
  }

  // this is for searching feature.
  handleChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }

  // search option and search value are already exist in state.
  handleSearch = () => {
    const { history } = this.props;
    const { searchOption, searchValue } = this.state;
    history.push(`/list?page=1&searchOption=${searchOption}&searchValue=${searchValue}`);
  }

  render() {
    const { articles, pagination, searchOption, searchValue } = this.state;
    const { pageTo, handleChange, handleSearch } = this;
    if (!articles || !pagination)
      return <div style={{ minHeight: '760px' }}>laoding...</div>
    return (
      <>
        <List
          articles={ articles }
        />
        <Paging
          pagination={ pagination }
          pageTo={ pageTo }
        />
        <Search
          onChange={ handleChange }
          onSearch={ handleSearch }
          searchOption={ searchOption }
          searchValue={ searchValue }
        />
      </>
    )
  }
}

export default ListContainer;