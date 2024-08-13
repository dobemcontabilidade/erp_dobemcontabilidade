import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnexoRequeridoPessoaDetailComponent } from './anexo-requerido-pessoa-detail.component';

describe('AnexoRequeridoPessoa Management Detail Component', () => {
  let comp: AnexoRequeridoPessoaDetailComponent;
  let fixture: ComponentFixture<AnexoRequeridoPessoaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnexoRequeridoPessoaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AnexoRequeridoPessoaDetailComponent,
              resolve: { anexoRequeridoPessoa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AnexoRequeridoPessoaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnexoRequeridoPessoaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load anexoRequeridoPessoa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AnexoRequeridoPessoaDetailComponent);

      // THEN
      expect(instance.anexoRequeridoPessoa()).toEqual(expect.objectContaining({ id: 123 }));
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
