import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { InstituicaoEnsinoDetailComponent } from './instituicao-ensino-detail.component';

describe('InstituicaoEnsino Management Detail Component', () => {
  let comp: InstituicaoEnsinoDetailComponent;
  let fixture: ComponentFixture<InstituicaoEnsinoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InstituicaoEnsinoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: InstituicaoEnsinoDetailComponent,
              resolve: { instituicaoEnsino: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(InstituicaoEnsinoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InstituicaoEnsinoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load instituicaoEnsino on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', InstituicaoEnsinoDetailComponent);

      // THEN
      expect(instance.instituicaoEnsino()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
