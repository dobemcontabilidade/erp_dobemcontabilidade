import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnexoOrdemServicoExecucaoDetailComponent } from './anexo-ordem-servico-execucao-detail.component';

describe('AnexoOrdemServicoExecucao Management Detail Component', () => {
  let comp: AnexoOrdemServicoExecucaoDetailComponent;
  let fixture: ComponentFixture<AnexoOrdemServicoExecucaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnexoOrdemServicoExecucaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AnexoOrdemServicoExecucaoDetailComponent,
              resolve: { anexoOrdemServicoExecucao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AnexoOrdemServicoExecucaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnexoOrdemServicoExecucaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load anexoOrdemServicoExecucao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AnexoOrdemServicoExecucaoDetailComponent);

      // THEN
      expect(instance.anexoOrdemServicoExecucao()).toEqual(expect.objectContaining({ id: 123 }));
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
