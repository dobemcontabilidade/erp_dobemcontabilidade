import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnexoRequeridoTarefaRecorrenteDetailComponent } from './anexo-requerido-tarefa-recorrente-detail.component';

describe('AnexoRequeridoTarefaRecorrente Management Detail Component', () => {
  let comp: AnexoRequeridoTarefaRecorrenteDetailComponent;
  let fixture: ComponentFixture<AnexoRequeridoTarefaRecorrenteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnexoRequeridoTarefaRecorrenteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AnexoRequeridoTarefaRecorrenteDetailComponent,
              resolve: { anexoRequeridoTarefaRecorrente: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AnexoRequeridoTarefaRecorrenteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnexoRequeridoTarefaRecorrenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load anexoRequeridoTarefaRecorrente on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AnexoRequeridoTarefaRecorrenteDetailComponent);

      // THEN
      expect(instance.anexoRequeridoTarefaRecorrente()).toEqual(expect.objectContaining({ id: 123 }));
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
