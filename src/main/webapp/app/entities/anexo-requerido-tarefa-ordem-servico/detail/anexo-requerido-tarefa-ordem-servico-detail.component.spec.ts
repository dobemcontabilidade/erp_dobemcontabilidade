import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnexoRequeridoTarefaOrdemServicoDetailComponent } from './anexo-requerido-tarefa-ordem-servico-detail.component';

describe('AnexoRequeridoTarefaOrdemServico Management Detail Component', () => {
  let comp: AnexoRequeridoTarefaOrdemServicoDetailComponent;
  let fixture: ComponentFixture<AnexoRequeridoTarefaOrdemServicoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnexoRequeridoTarefaOrdemServicoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AnexoRequeridoTarefaOrdemServicoDetailComponent,
              resolve: { anexoRequeridoTarefaOrdemServico: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AnexoRequeridoTarefaOrdemServicoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnexoRequeridoTarefaOrdemServicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load anexoRequeridoTarefaOrdemServico on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AnexoRequeridoTarefaOrdemServicoDetailComponent);

      // THEN
      expect(instance.anexoRequeridoTarefaOrdemServico()).toEqual(expect.objectContaining({ id: 123 }));
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
