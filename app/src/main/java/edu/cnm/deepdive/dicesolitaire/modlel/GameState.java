package edu.cnm.deepdive.dicesolitaire.modlel;

public enum  GameState {

  /**Initial state before the first roll. */
  INITIAL,
  //** State starting with first roll, unitl maximum scratch count is reached. /*
  IN_PROGRESS,
  /**State after maximum scratch count is reached. */
  TERMINAL;

}
