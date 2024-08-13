import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TermoContratoContabilDetailComponent } from './termo-contrato-contabil-detail.component';

describe('TermoContratoContabil Management Detail Component', () => {
  let comp: TermoContratoContabilDetailComponent;
  let fixture: ComponentFixture<TermoContratoContabilDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermoContratoContabilDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TermoContratoContabilDetailComponent,
              resolve: { termoContratoContabil: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TermoContratoContabilDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TermoContratoContabilDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load termoContratoContabil on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TermoContratoContabilDetailComponent);

      // THEN
      expect(instance.termoContratoContabil()).toEqual(expect.objectContaining({ id: 123 }));
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
