import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EtapaFluxoModeloDetailComponent } from './etapa-fluxo-modelo-detail.component';

describe('EtapaFluxoModelo Management Detail Component', () => {
  let comp: EtapaFluxoModeloDetailComponent;
  let fixture: ComponentFixture<EtapaFluxoModeloDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EtapaFluxoModeloDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EtapaFluxoModeloDetailComponent,
              resolve: { etapaFluxoModelo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EtapaFluxoModeloDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EtapaFluxoModeloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load etapaFluxoModelo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EtapaFluxoModeloDetailComponent);

      // THEN
      expect(instance.etapaFluxoModelo()).toEqual(expect.objectContaining({ id: 123 }));
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
