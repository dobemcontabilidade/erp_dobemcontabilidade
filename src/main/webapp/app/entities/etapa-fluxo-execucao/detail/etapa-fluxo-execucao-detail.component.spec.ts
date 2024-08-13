import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EtapaFluxoExecucaoDetailComponent } from './etapa-fluxo-execucao-detail.component';

describe('EtapaFluxoExecucao Management Detail Component', () => {
  let comp: EtapaFluxoExecucaoDetailComponent;
  let fixture: ComponentFixture<EtapaFluxoExecucaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EtapaFluxoExecucaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EtapaFluxoExecucaoDetailComponent,
              resolve: { etapaFluxoExecucao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EtapaFluxoExecucaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EtapaFluxoExecucaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load etapaFluxoExecucao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EtapaFluxoExecucaoDetailComponent);

      // THEN
      expect(instance.etapaFluxoExecucao()).toEqual(expect.objectContaining({ id: 123 }));
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
