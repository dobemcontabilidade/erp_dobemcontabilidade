import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SubTarefaRecorrenteDetailComponent } from './sub-tarefa-recorrente-detail.component';

describe('SubTarefaRecorrente Management Detail Component', () => {
  let comp: SubTarefaRecorrenteDetailComponent;
  let fixture: ComponentFixture<SubTarefaRecorrenteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubTarefaRecorrenteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SubTarefaRecorrenteDetailComponent,
              resolve: { subTarefaRecorrente: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SubTarefaRecorrenteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubTarefaRecorrenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subTarefaRecorrente on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SubTarefaRecorrenteDetailComponent);

      // THEN
      expect(instance.subTarefaRecorrente()).toEqual(expect.objectContaining({ id: 123 }));
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
