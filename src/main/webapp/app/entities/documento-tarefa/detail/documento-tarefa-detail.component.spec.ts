import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DocumentoTarefaDetailComponent } from './documento-tarefa-detail.component';

describe('DocumentoTarefa Management Detail Component', () => {
  let comp: DocumentoTarefaDetailComponent;
  let fixture: ComponentFixture<DocumentoTarefaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocumentoTarefaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DocumentoTarefaDetailComponent,
              resolve: { documentoTarefa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DocumentoTarefaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentoTarefaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documentoTarefa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DocumentoTarefaDetailComponent);

      // THEN
      expect(instance.documentoTarefa()).toEqual(expect.objectContaining({ id: 123 }));
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
