import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EnderecoPessoaDetailComponent } from './endereco-pessoa-detail.component';

describe('EnderecoPessoa Management Detail Component', () => {
  let comp: EnderecoPessoaDetailComponent;
  let fixture: ComponentFixture<EnderecoPessoaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnderecoPessoaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EnderecoPessoaDetailComponent,
              resolve: { enderecoPessoa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EnderecoPessoaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EnderecoPessoaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load enderecoPessoa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EnderecoPessoaDetailComponent);

      // THEN
      expect(instance.enderecoPessoa()).toEqual(expect.objectContaining({ id: 123 }));
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
