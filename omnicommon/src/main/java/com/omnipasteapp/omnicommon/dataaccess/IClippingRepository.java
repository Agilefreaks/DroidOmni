package com.omnipasteapp.omnicommon.dataaccess;

import com.omnipasteapp.omnicommon.domain.Clipping;

import java.util.List;

public interface IClippingRepository {
  List<Clipping> getForLast24Hours();

  void save(Clipping clipping);
}
