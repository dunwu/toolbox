import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import { decryptReq, encryptReq } from '@/services/tool';
import { Button, Card, Col, Icon, Input, message, Radio, Row, Tabs } from 'antd';
import { connect } from 'dva';
import React, { Component } from 'react';

const { TextArea } = Input;

@connect()
class EncodeToolPage extends Component {
  state = {
    content: {
      key: '',
      type: 'AES',
      plaintext: '测试内容',
      ciphertext: '',
    },
  };

  onChangePlaintext = value => {
    const { content } = this.state;
    content.plaintext = value;
    this.setState({ content });
  };

  onChangeCiphertext = value => {
    const { content } = this.state;
    content.ciphertext = value;
    this.setState({ content });
  };

  onChangeType = value => {
    const { content } = this.state;
    content.type = value;
    this.setState({ content });
  };

  onChangeKey = value => {
    const { content } = this.state;
    content.key = value;
    this.setState({ content });
  };

  handleEncode = () => {
    const { content } = this.state;

    if (!content.plaintext) {
      message.error('明文内容为空');
    }

    content.ciphertext = '';
    encryptReq(content)
      .then(res => {
        if (res.success) {
          this.setState({
            content: res.data,
          });
        } else {
          message.error(res.message);
        }
      })
      .catch(err => {
        message.error(err);
      });
  };

  handleDecode = () => {
    const { content } = this.state;
    if (!content.ciphertext) {
      message.error('密文内容为空');
    }

    content.plaintext = '';
    decryptReq(content)
      .then(res => {
        if (res.success) {
          this.setState({
            content: res.data,
          });
        } else {
          message.error(res.message);
        }
      })
      .catch(err => {
        message.error(err);
      });
  };

  render() {
    const { content } = this.state;
    const aesTypes = ['AES', 'AES_ECB_PKCS5PADDING', 'AES_CBC_PKCS5PADDING', 'AES_CBC_NOPADDING'];
    const desTypes = ['DES', 'DES_ECB_PKCS5PADDING', 'DES_CBC_PKCS5PADDING', 'DES_CBC_NOPADDING'];

    return (
      <PageHeaderWrapper
        title="编码工具"
        logo={<Icon type="codepen" />}
        content="在线数字摘要、加密算法工具"
      >
        <Card bordered={false}>
          <Tabs defaultActiveKey="1">
            <Tabs.TabPane tab="加密解密" key="1">
              <Row>
                <Radio.Group onChange={e => this.onChangeType(e.target.value)} value={content.type}>
                  {[...Array(aesTypes.length).keys()].map(i => (
                    <Radio key={`aes-${i}`} value={aesTypes[i]}>
                      {aesTypes[i]}
                    </Radio>
                  ))}
                </Radio.Group>
                <br />
                <Radio.Group onChange={e => this.onChangeType(e.target.value)} value={content.type}>
                  {[...Array(desTypes.length).keys()].map(i => (
                    <Radio key={`des-${i}`} value={desTypes[i]}>
                      {desTypes[i]}
                    </Radio>
                  ))}
                </Radio.Group>
              </Row>
              <Row gutter={24}>
                <Col span={10}>
                  <TextArea
                    rows={10}
                    value={content.plaintext}
                    onChange={e => this.onChangePlaintext(e.target.value)}
                  />
                </Col>
                <Col span={4}>
                  <Row>
                    <Input
                      placeholder="input with clear icon"
                      allowClear
                      onChange={e => this.onChangeKey(e.target.value)}
                    />
                  </Row>
                  <Row>
                    <Button type="primary" icon="double-ciphertext" onClick={this.handleEncode}>
                      加密
                    </Button>
                  </Row>
                  <Row>
                    <Button type="primary" icon="double-plaintext" onClick={this.handleDecode}>
                      解密
                    </Button>
                  </Row>
                </Col>
                <Col span={10}>
                  <TextArea
                    rows={10}
                    value={content.ciphertext}
                    onChange={e => this.onChangeCiphertext(e.target.value)}
                  />
                </Col>
              </Row>
            </Tabs.TabPane>
            <Tabs.TabPane tab="Tab 2" key="2">
              Content of Tab Pane 2
            </Tabs.TabPane>
            <Tabs.TabPane tab="Tab 3" key="3">
              Content of Tab Pane 3
            </Tabs.TabPane>
          </Tabs>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default EncodeToolPage;
