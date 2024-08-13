import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AgendaTarefaOrdemServicoExecucaoDetailComponent } from './agenda-tarefa-ordem-servico-execucao-detail.component';

describe('AgendaTarefaOrdemServicoExecucao Management Detail Component', () => {
  let comp: AgendaTarefaOrdemServicoExecucaoDetailComponent;
  let fixture: ComponentFixture<AgendaTarefaOrdemServicoExecucaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgendaTarefaOrdemServicoExecucaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AgendaTarefaOrdemServicoExecucaoDetailComponent,
              resolve: { agendaTarefaOrdemServicoExecucao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AgendaTarefaOrdemServicoExecucaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgendaTarefaOrdemServicoExecucaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load agendaTarefaOrdemServicoExecucao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AgendaTarefaOrdemServicoExecucaoDetailComponent);

      // THEN
      expect(instance.agendaTarefaOrdemServicoExecucao()).toEqual(expect.objectContaining({ id: 123 }));
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
